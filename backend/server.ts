/**
 * Created by vili on 01/02/2017.
 */

import express = require("express");

import bodyParser = require("body-parser");

import request = require("request");

import {WeekParser} from "./weekparser";

import * as schedule from "node-schedule";

const app = express();

// Menu is stored in this variable and updated every hour
// This variable is served at route /menu
let campusravita = {
    en: {
        error: false,
        menu: []
    },
    fi: {
        error: false,
        menu: []
    }
};

app.use(bodyParser.json());

// Add CORS headers
app.all("/*", (req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Content-Type");
    res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");

    if (req.method === 'OPTIONS') {
        res.status(200).end();
    } else {
        next();
    }
});

// Serve cached menu or error message, depending on the situation
app.get("/menu", (req, res) => {
    let lang = req.query.lang == undefined ? "fi" : req.query.lang;

    if (campusravita[lang].error) {
        res.status(500);
        res.json({
            message: "An error occured while parsing Campusravita's website."
        });
    } else {
        res.json(campusravita[lang].menu);
    }
});


// Refreshes cached menu by parsing campusravita's website
function refresh(languages: Array<string>) {

    languages.forEach(language => {
        console.log("REFRESH: START [" + language + "]");

        campusravita[language].error = false;

        let url = language === "fi" ? "http://campusravita.fi/ruokalista" : "http://campusravita.fi/en/ruokalista";

        request(url, function (error, response, body) {
            if (!error) {
                let week = new WeekParser(body);

                if (week.isValid()) {
                    campusravita[language].menu = week.getDays();

                    console.log("REFRESH: THIS WEEK PARSED [" + language + "]");

                    // Check if next weeks menu is also available
                    request(url + '/seuraavaviikko/', function (error, response, body) {
                        if (!error) {
                            let nextWeek = new WeekParser(body);

                            if (nextWeek.isValid()) {
                                nextWeek.getDays().forEach((day) => {
                                    campusravita[language].menu.push(day);
                                });

                                console.log("REFRESH: NEXT WEEK PARSED [" + language + "]");
                            }
                        }

                        console.log("REFRESH: END [" + language + "]");
                    });
                } else {
                    console.log("REFRESH: ERROR WHILE PARSING [" + language + "]");
                    campusravita[language].error = true;
                }
            } else {
                console.log("REFRESH: ERROR WHILE LOADING [" + language + "]");
                campusravita[language].error = true;
            }
        });
    });
}

app.listen(3000, () => {
    console.log("Server is running! Yey!");
    refresh(["fi", "en"]);
});

// Update cache every hour
schedule.scheduleJob('0 * * * *', () => refresh(["fi", "en"]));
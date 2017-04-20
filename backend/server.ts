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
let campusravita = [];

// If there were errors parsing this weeks menu
// Requests get error message when this is true
let parsingError = false;

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
    if (parsingError) {
        res.status(500);
        res.json({
            message: "An error occured while parsing Campusravita's website."
        });
    } else {
        res.json(campusravita);
    }
});


// Refreshes cached menu by parsing campusravita's website
function refresh() {
    console.log("REFRESH: START");

    parsingError = false;

    request('http://campusravita.fi/ruokalista/', function (error, response, body) {
        if (!error) {
            let week = new WeekParser(body);

            if (week.isValid()) {
                campusravita = week.getDays();

                console.log("REFRESH: THIS WEEK PARSED");

                // Check if next weeks menu is also available
                request('http://campusravita.fi/ruokalista/seuraavaviikko/', function (error, response, body) {
                    if (!error) {
                        let nextWeek = new WeekParser(body);

                        if (nextWeek.isValid()) {
                            nextWeek.getDays().forEach((day) => {
                                campusravita.push(day);
                            });

                            console.log("REFRESH: NEXT WEEK PARSED");
                        }
                    }

                    console.log("REFRESH: END");
                });
            } else {
                console.log("REFRESH: ERROR WHILE PARSING");
                parsingError = true;
            }
        } else {
            console.log("REFRESH: ERROR WHILE LOADING");
            parsingError = true;
        }
    });
}

app.listen(3000, () => {
    console.log("Server is running! Yey!");
    refresh();
});

// Update cache every hour
schedule.scheduleJob('0 * * * *', refresh);
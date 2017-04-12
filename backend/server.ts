/**
 * Created by vili on 01/02/2017.
 */

import express = require("express");

import bodyParser = require("body-parser");

import request = require("request");

import { WeekParser } from "./weekparser";

import * as schedule from "node-schedule";

const app = express();

let campusravita = [];
let parsingError = false;

app.use(bodyParser.json());

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

app.get("/menu", (req, res) => {
    if (parsingError) {
        refresh();

        res.status(500);
        res.json({
            message: "An error occured while parsing Campusravita's website."
        });
    } else {
        res.json(campusravita);
    }
});

function refresh() {
    parsingError = false;

    request('http://campusravita.fi/ruokalista/', function (error, response, body) {
        if (!error) {
            let week = new WeekParser(body);

            if (week.isValid()) {
                campusravita = week.getDays();

                request('http://campusravita.fi/ruokalista/seuraavaviikko/', function (error, response, body) {
                    if (!error) {
                        let nextWeek = new WeekParser(body);

                        if (nextWeek.isValid()) {
                            campusravita.concat(nextWeek);
                        }
                    }
                });
            } else {
                parsingError = true;
            }
        } else {
            parsingError = true;
        }
    });
}

app.listen(3000, () => {
    console.log("Server is running! Yey!");
    refresh();
});

schedule.scheduleJob('0 6 * * *', refresh);
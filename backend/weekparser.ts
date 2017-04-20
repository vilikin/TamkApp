/**
 * Created by vili on 24/02/2017.
 */

const util = require('util');

interface Diet {
    name: string;
    abbreviation: string;
}

interface Option {
    name: string;
    details?: string;
    diets?: Array<Diet>;
    priceForStudent: number;
    priceForStaff: number;
    priceForOthers: number;
}

interface Meal {
    name: string;
    options: Array<Option>
}

interface Day {
    date: Date;
    meals?: Array<Meal>
}

export class WeekParser {
    private menuHtml: string;
    private week: Array<Day> = [];
    private valid: boolean = true;

    constructor(private completeHtml: string) {
        this.menuHtml = completeHtml;

        let daysRawHtml = this.menuHtml.split('<div class="view-grouping">');
        daysRawHtml.splice(0, 1);

        //console.log(daysRawHtml.length + " days detected");

        if (daysRawHtml.length === 0) {
            //console.log("Unable to parse, didn't find any days.");
            this.valid = false;
            return;
        }

        try {
            this.week = daysRawHtml.map((rawDayHtml) => {

                let dateRaw = rawDayHtml.split('datatype="xsd:dateTime" content="')[1].split('">')[0];
                let date = new Date(dateRaw);

                let rawMeals = rawDayHtml.split('<h3>');
                rawMeals.splice(0, 1);

                let meals: Array<Meal> = rawMeals.map((rawMeal) => {
                    let name = rawMeal.split("</h3>")[0];

                    let optionsRaw = rawMeal.split('<div class="field field-name-field-nimi field-type-taxonomy-term-reference field-label-hidden"><div class="field-items"><div class="field-item even">');
                    optionsRaw.splice(0, 1);

                    let options: Array<Option> = optionsRaw.map((rawOption) => {
                        let name = rawOption.split("</div>")[0];
                        let details;

                        if (rawOption.indexOf('<div class="field field-name-field-lisatiedot field-type-text field-label-hidden"><div class="field-items"><div class="field-item even">') !== -1) {
                            details = rawOption.split('<div class="field field-name-field-lisatiedot field-type-text field-label-hidden"><div class="field-items"><div class="field-item even">')[1].split("</div>")[0];
                        }

                        let dietSplit = rawOption.split('<div  class="ds-1col taxonomy-term vocabulary-ruokavaliot view-mode-full clearfix">');
                        let diets: Array<Diet> = [];

                        if (dietSplit.length > 1) {
                            dietSplit.forEach((dietRaw, index) => {
                                if (index != 0) {
                                    let abbreviation = dietRaw.split("<p>")[1].split("</p>")[0];
                                    let name = dietRaw.split("<p>")[2].split("</p>")[0];

                                    diets.push({
                                        name: name,
                                        abbreviation: abbreviation
                                    });
                                }
                            });
                        }

                        let studentPrice = this.getPriceFor(rawOption, "opiskelija");
                        let staffPrice = this.getPriceFor(rawOption, "henkilokunta");
                        let othersPrice = this.getPriceFor(rawOption, "muut");

                        return {
                            name: name,
                            details: details,
                            diets: diets,
                            priceForStudent: studentPrice,
                            priceForStaff: staffPrice,
                            priceForOthers: othersPrice
                        }
                    });

                    return {
                        name: name,
                        options: options
                    }
                });

                return {
                    date: date,
                    meals: meals
                }
            });
        } catch (err) {
            console.log("ERROR WHILE PARSING HTML:");
            console.log(err);
            this.valid = false;
        }
    }

    print() {
        console.log(util.inspect(this.week, false, null));
    }

    getDays() {
        return this.week;
    }

    isValid() {
        return this.valid;
    }

    private getPriceFor(mealHtml: string, group:string): number {
        let raw = mealHtml.split('<div class="field field-name-field-hinta-'+group+' field-type-number-decimal field-label-hidden"><div class="field-items"><div class="field-item even">');

        return raw.length > 1 ? parseFloat(raw[1].split("€")[0]) : 0;
    }
}

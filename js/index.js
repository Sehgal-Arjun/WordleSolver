import jsonguesses from './valid_guesses.json' assert {type:"json"};
import jsonanswers from './valid_solutions.json' assert {type:"json"};
import origlistofwords from './unigram_freq.json' assert {type:"json"};

let validguesses = []
let validanswers = []
let newlistofwords = [];
let listofwords = [];

let refinedanswers = [];

let blackletters = [];

let count = 0;

for (const element of jsonguesses){
    validguesses.push(element.word);
}
for (const element of jsonanswers){
    validanswers.push(element.word);
}
for (const element of origlistofwords){
    if (element.word != null){
        newlistofwords.push(element);
    }
}

for (const element of newlistofwords){
    if (element.word.toString().length == 5){
       
        listofwords.push(element);
    }
}

for (const element of jsonanswers){
    validguesses.push(element.word);
}

refinedanswers = validguesses;

let currentrow = 1;

let alldivs = document.getElementsByClassName("wordlegriditem");
for (let i = 0; i < alldivs.length; i++){
    alldivs[i].addEventListener('click', function(){
        if (alldivs[i].id.charAt(0) == currentrow){
            if (alldivs[i].style.backgroundColor == 'gray'){
                alldivs[i].style.backgroundColor = 'yellow';
            }
            else if (alldivs[i].style.backgroundColor == 'yellow'){
                alldivs[i].style.backgroundColor = 'green'
            }
            else if (alldivs[i].style.backgroundColor == 'green'){
                alldivs[i].style.backgroundColor = 'gray';
            }
            else{
                alldivs[i].style.backgroundColor = 'gray';
            }
            
        }
    });
}

let inputs = document.getElementsByClassName("input-field");
for (let i = 0; i < inputs.length; i++){
    inputs[i].addEventListener('keypress', function(){
        if (inputs[i].value.length > 0){
            console.log('poo');
            inputs[i].value = 'a';
        }
    })
}

let submitbutton = document.getElementById('inputbutton');
submitbutton.addEventListener('click', function(){
    let working = true;
    for (let i = 1; i < 6; i++){
        if (document.getElementById(currentrow + "" + i + " input").value.length != 1){
            working = false;
            console.log(working);
        }
    }
    if (working){
        //let response = document.getElementById("wordleresults").value;
        let word = "";
        for (let i = 1; i < 6; i++){
            word = word + document.getElementById(currentrow + "" + i + " input").value;
        }

        let response = "";
        for (let i = 1; i < 6; i++){
            let col = document.getElementById(currentrow + "" + i).style.backgroundColor.toLowerCase();
            if (col == "green") { col = "g"; }
            else if (col == "gray") { col = "b"; }
            else if (col == "yellow") { col = "y"; }
            else { col == "b"; }
            response = response + col;
        }
        console.log('response: ' + response);
        makemove(word, response);
        disableediting(currentrow, word, response);
        currentrow++;
        initnewrow(currentrow);
    }
})

function disableediting(row, wordstr, responsestr){
    let divs = document.getElementsByClassName("wordlegriditem");
    let counter = 0;
    let word = wordstr.split("");
    let response = responsestr.split("");
    let color = 'gray';
    for (let i = 0; i < divs.length; i++){
        if (divs[i].id.charAt(0) == currentrow){
            if (response[counter] == 'g'){
                color = 'green';
            }
            else if (response[counter] == 'y'){
                color = 'yellow';
            }
            else if (response[counter] == 'b'){
                color = 'gray';
            }
            else {
                color = 'gray';
            }
            divs[i].innerHTML = '<h1>' + word[counter] + '</h1>';
            divs[i].style.backgroundColor = color;
            counter++;
        }
    }
}

function initnewrow(row){
    let divs = document.getElementsByClassName("wordlegriditem");
    let counter = 1;
    for (let i = 0; i < divs.length; i++){
        if (divs[i].id.charAt(0) == currentrow){
            divs[i].innerHTML = '<input id = "' + currentrow + counter + ' input" type="text" class="input-field"/>';
            divs[i].style.backgroundColor = 'gray';
            counter++;
        }
    }
}

function makemove(wordparam, response){
    let result = response.split("");
    let removelist = [];
    let word = wordparam.split("");

    for (let i = 0; i < result.length; i++){
        if (result[i] == 'b'){
            for (const element of refinedanswers){
                if (!element.includes(word[i])){
                    if (!blackletters.includes(word[i])){
                        blackletters.push(word[i]);
                    }
                }
            }
        }

        if (result[i] == 'y'){
            for (const element of refinedanswers){
                let currentword = element;
                let yellowletter = word[i];
                if (currentword.split("").includes(yellowletter)){
                    if (currentword.split("")[i] == yellowletter){
                        removelist.push(currentword);
                    }
                }
                else{
                    removelist.push(element);
                }
            }
        }

        if (result[i] == 'g'){
            for (const element of refinedanswers){
                if (element.split("")[i] != word[i]){
                    removelist.push(element);
                }
            }
        }
    }

    for (const element of refinedanswers){
        let contains = false;
        for (let j = 0; j < blackletters.length; j++){
            if (element.split("").includes(blackletters[j] + "")){
                contains = true;
            }
        }

        if (contains){
            removelist.push(element)
        }
    }

    for (const element of removelist){
        if (refinedanswers.includes(element)){
            let index = refinedanswers.indexOf(element);
            refinedanswers.splice(index, 1);
        }
    }
    count = count + 1;

    if (result != ['g', 'g', 'g', 'g', 'g']){
        let finalword = refinedanswers[Math.floor(Math.random(0, refinedanswers.length))];
        let words = [];
        let wordfreq = [];
        let score = 0;

        let finalelement;
        let validansweraslist = [];
        for (let i = 0; i < validanswers.length; i++){
            validansweraslist.push(validanswers[i]);
        }
        for (let i = 0; i < listofwords.length; i++){
            words.push(listofwords[i].word);
            wordfreq.push(listofwords[i].count);
        }
        for (let i = 0; i < refinedanswers.length; i++){
            if (!words.includes(refinedanswers[i])){
                score = 0;
            }
            else{
                let elementword = refinedanswers[0].word;
                let elementcount = refinedanswers[0].count;
                for (let j = 0; j < listofwords.length; j++){
                    if (listofwords[j].word == refinedanswers[i]){
                        elementword = listofwords[j].word;
                        elementcount = listofwords[j].count;
                    }
                }
                if (elementcount > score){
                    if (validansweraslist.includes(elementword)){
                        score = elementcount;
                        finalelement = elementword;
                    }
                }
            }
        }

        finalword = finalelement;
        document.getElementById("responsehere").innerHTML = '<h2>Use: ' + finalword + '</h2>';
        document.getElementById('responsehere').innerHTML = document.getElementById('responsehere').innerHTML + '<button id = "listbtn" class="action-button">See all options</button>';

        document.getElementById('listbtn').addEventListener('click', function(){
            let list = refinedanswers[0];
            for (let i = 1; i < refinedanswers.length; i++){
                list = list + ", " + refinedanswers[i];
            }
            document.getElementById('responsehere').innerHTML = 'Use: ' + finalword + "<br>" + list;
        })
    }
    else{
        document.getElementById("responsehere").innerHTML = "Nice! You got in it " + count + " tries!";
    }
};
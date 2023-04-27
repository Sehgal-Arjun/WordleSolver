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

console.log(listofwords);
//console.log(validanswers);
//console.log(refinedanswers);

let button = document.getElementById("btn");
button.addEventListener('click', function(){
    let result = document.getElementById("wordleresults").value.split("");
    let removelist = [];
    let word = document.getElementById("userword").value.split("");

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

    console.log(refinedanswers);

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
        document.getElementById("responsehere").innerHTML = 'Use: ' + finalword;
        document.getElementById('responsehere').innerHTML = document.getElementById('responsehere').innerHTML + '<br><button id="listbtn">See Other Options</button>';
        document.getElementById("yourguesses").innerHTML = document.getElementById("yourguesses").innerHTML + "<br>" +word.join("") + "<br>"; 

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

    console.log("refinedanswers:");
    console.log(refinedanswers)
});
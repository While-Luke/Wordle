var currentWord = [" ", " ", " ", " ", " "];
var currentLetter = 0;
var guesses = 0;
const xhttp = new XMLHttpRequest();

function keyDown(){
    if(event.key >= 'a' && event.key <= 'z'){
        if(currentLetter < 5){
            currentWord[currentLetter] = event.key.toUpperCase(); 
            currentLetter++;
        }
    }
    else if(event.key == "Backspace"){
        currentLetter--;
        currentWord[currentLetter] = " ";
    }
    else if(event.key == "Enter"){
        if(currentLetter == 5) sendRequest();
    }

    const lines = document.getElementsByTagName("tr");
    const line = lines[guesses];
    const tds = line.getElementsByTagName("td");
    for(i = 0; i < 5; i++){
        tds[i].innerHTML = currentWord[i];
    }
}

function sendRequest(){
    fetch(`http://localhost:8080/?guess=${currentWord.join("")}`, {
        method: 'GET'})
    .then(response => response.json())
    .then(result => checkWord(result));  
}

function checkWord(correct){
    const lines = document.getElementsByTagName("tr");
    const line = lines[guesses];
    const tds = line.getElementsByTagName("td");
    for(i = 0; i < 5; i++){
        if(correct[i] == "N") tds[i].classList.add("wrong");
        else if(correct[i] == "Y") tds[i].classList.add("nearly");
        else if(correct[i] == "G") tds[i].classList.add("correct");
    }

    currentWord = [" ", " ", " ", " ", " "];
    currentLetter = 0;
    guesses++;

    let win = true;
    for(i = 0; i < 5; i++){
        if(correct[i] != "G") win = false;
    }

    if(win){
        alert("You win!");
    }
    else if(guesses == 6){
        alert("You lose!");
    }
}

window.addEventListener("keydown", keyDown, false);
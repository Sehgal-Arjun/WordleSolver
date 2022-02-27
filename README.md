Created by Arjun Sehgal on the 14th of February, 2022.

```NOTE```: To use the files, please replace my filepaths with your own once you download the code. There are filepaths to replace in the ```Main.java``` and ```Testing.java``` files.

This program is a simple wordle solver which gives the user recommendations of the next word to input and keeps track of all possible answers.

It uses three ```.csv``` files: one for a list of valid Wordle guesses, one for a list of valid Wordle solutions, and one for a list of 1/3 of a million words in the English language, along with how often they are used. I use this last list to attempt to optimize the algorithm's suggestions, rather than leaving it completely random. 

When prompted to enter the word you typed into Wordle, ensure that it is a 5-letter word (unless you type in "```list```", "```reroll```", or "```done```"). The bot always selects the most commonly used word from the remaining possibilities, but sometimes it might be obvious to the user that that word will not be the solution. If that is the case, you can use the "```list```" command, as described in the next paragraph.

To see the list of all possbilities, type "```list```", and you will be able to see every possible remaining solution. From here, you can pick by yourself and decide which word seems most likely to be the wordle answer.

Eg: if you use "```list```" and it shows you the following:

```"baboo, bacco, baloo, bazoo, blook, bloop, blowy, bobac, bobak, bobol, bocca, boffo, bombo, booay, boody, boofy, booky, boomy, boppy, boyla, block, blood, bloom, bobby, booby, boozy"```

It is quite clear that almost all of these words are not going to be the solution, they are merely used to gather information while narrowing down possibilities. From that list, I would cancel the (possible) solutions down to: "```booky```", "```block```", "```blood```", "```bloom```", and maybe "```bobby```". These are quite similar, so you could pick any one and return a more narrowed down list.


Once you get the word, when prompted for your input, simply enter "```done```", and the program will end.

There is also a ```wordleBotSolverResults.csv``` file, which was created from the ```Testing.java``` file. It has a list of all Wordle solutions, each one solved by my Wordle Solver. It contains the word, the list of words the algorithm used to find it, and the amount of guesses it took.

I hope this helps!



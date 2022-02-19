Created by Arjun Sehgal on the 14th of February, 2022.

This program is a simple wordle solver which gives the user recommendations of the next word to input and keeps track of all possible answers.
When prompted to enter the word you typed into Wordle, ensure that it is a 5-letter word (unless you type in "```list```" or "```reroll```"). Also, you do not have to pick the word the algorithm suggests, as it is merely a random word from the possible solutions.

If you do not like the word it suggests, simply enter "```reroll```" when prompted with what you typed into Wordle. It will randomize the recommended word. If you do this, and it gives you the same word, then the list of all possibilities would likely be quite small. To see the list of all possbilities, instead of typing "```reroll```", type "```list```", and you will be able to see every possible remaining solution. From here, you can pick by yourself and decide which word seems most likely to be the wordle answer.

Eg: if you use "list" and it shows you the following:

```"baboo, bacco, baloo, bazoo, blook, bloop, blowy, bobac, bobak, bobol, bocca, boffo, bombo, booay, boody, boofy, booky, boomy, boppy, boyla, block, blood, bloom, bobby, booby, boozy"```

It is quite clear that almost all of these words are not going to be the solution, they are merely used to gather information while narrowing down possibilities. From that list, I would cancel the (possible) solutions down to: "```booky```", "```block```", "```blood```", "```bloom```", and maybe "```bobby```". These are quite similar, so you could pick any one and return a more narrowed down list.

I hope this helps!



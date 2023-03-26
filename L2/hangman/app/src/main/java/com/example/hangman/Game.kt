package com.example.hangman

class Game(word: String) {
    var word: String
    var numberOfFailedGuesses: Int = 0
    var guessedChars: MutableList<Char> = mutableListOf()
    var gameOver: Boolean = false
    var gameWon: Boolean = false

    init {
        this.word = word
    }

    fun isWon(): Boolean{
        var tmpWord = word.toMutableList()
        for (char in guessedChars){
            while (tmpWord.contains(char)){
                tmpWord.remove(char)
            }
        }
        return tmpWord.size == 0
    }
    // returns false if letter not found
    fun handleOnClick(letter: CharSequence): Boolean{
        var guess = letter.first().lowercaseChar()
        guessedChars.add(guess)
        if (!word.contains(guess)){
            numberOfFailedGuesses++
        }
        if (numberOfFailedGuesses >= 9){
            gameOver = true
        }else if (isWon()){
            gameWon = true
        }
        return word.contains(guess)
    }

    fun toText(): String{
        var toBePrinted: CharArray = CharArray(word.length)
        for (i in toBePrinted.indices){
            toBePrinted[i] = '?'
        }
        for (char in guessedChars){
            if (word.contains(char)){
                toBePrinted[word.indexOf(char)] = char
            }
        }
        return String(toBePrinted)
    }
}
package gallego.morales.entrega

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import gallego.morales.entrega.databinding.GameFragmentBinding

class GameViewModel : ViewModel() {
    // contenido del campo de la letra
    val entrada_letra = MutableLiveData<String>()
    val _entrada_game: LiveData<String>
        get() = _entrada_game




    private val _status_game = MutableLiveData<String>()
    val status_game: LiveData<String>
        get() = _status_game

    private val _word_enter = MutableLiveData<String>()
    val word_enter: LiveData<String>
        get() = _word_enter

    private val _word_secret = MutableLiveData<String>()
    val word_secret: LiveData<String>
        get() = _word_secret

    private val _word_show = MutableLiveData<String>()
    val word_show: LiveData<String>
        get() = _word_show


    private val _lives = MutableLiveData<Int>()
    val lives: LiveData<Int>
        get() = _lives

    private val _countOk = MutableLiveData<Int>()
    val countOk: LiveData<Int>
        get() = _countOk
    private var _live_draw = MutableLiveData<Int>()
    val live_draw: LiveData<Int>
        get() = _live_draw


    private val listDraw = listOf(
        R.drawable.ahorcado0,
        R.drawable.ahorcado1,
        R.drawable.ahorcado2,
        R.drawable.ahorcado3,
        R.drawable.ahorcado4,
        R.drawable.ahorcado5,
        R.drawable.ahorcado6
    )
    private var charsExist = ArrayList<Char>()//list of characters representing attempts
    var listSet = listOf(
        "Super man",
        "Capitana Marvel",
        "Doctor strange",
        "Hulk",
        "Batman",
        "Thor",
        "Iron man"
    )


    fun getRandomString(): String {
        return listSet.random().toString()
    }

    init {
        _lives.value = 0
        _countOk.value = 0
        _word_show.value = replaceLetter(getRandomString())
        _word_secret.value =  getRandomString()
        _live_draw.value = listDraw[0]
        _status_game.value = "jugando"
    }

    private lateinit var binding: GameFragmentBinding
    private fun replaceCheckLetter(str: String, myChar: Char): String {
        val chars: CharArray = str.toCharArray()
        if (!chars.contains(myChar.uppercaseChar()) && !chars.contains(myChar.lowercaseChar())) {
            _lives.value = (_lives.value)?.plus(1)
            _live_draw.value = listDraw[_lives.value?.toInt()!!]
        } else {
            if (!charsExist.contains(myChar)) {
                charsExist.add(myChar)
                _countOk.value = (_countOk.value)?.plus(1)
                //println("Congratulations on your hit number: $countOk")
            }
        }
        for (i in chars.indices) {
            if (chars[i] != ' ' && chars[i] != myChar.lowercaseChar() && chars[i] != myChar.uppercaseChar()) {
                if (!charsExist.contains(chars[i].lowercaseChar()) && !charsExist.contains(chars[i].uppercaseChar())) {
                    chars[i] = '_'
                }
            }
        }
        return chars.concatToString()
    }

    fun intento() {
        var letter = entrada_letra.value.toString()[0]
        print(letter)
       val result: String = replaceCheckLetter(_word_secret.value.toString(), letter)
        _word_show.value = result
        _live_draw.value = listDraw[_lives.value?.toInt()!!]
        if(result==_word_secret.value.toString()){
            _status_game.value="ganaste"
        }
        if(_lives.value==6){
            _status_game.value="perdiste"
        }

    }


    fun replaceLetter(str: String): String {
        val chars: CharArray = str.toCharArray()
        for (i in chars.indices) {
            if (chars[i] != ' ') {
                chars[i] = '_'
            }
        }
        return chars.concatToString()
    }


}
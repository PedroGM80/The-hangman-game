package gallego.morales.entrega

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gallego.morales.entrega.GameFragment.Companion.fail
import gallego.morales.entrega.GameFragment.Companion.ok
import gallego.morales.entrega.databinding.GameFragmentBinding
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    val entrada_letra = MutableLiveData<String>()
    private val _entrada_game: LiveData<String>
        get() = _entrada_game

    fun replaceLetter(str: String): String {
        val chars: CharArray = str.toCharArray()
        for (i in chars.indices) {
            if (chars[i] != ' ') {
                chars[i] = '_'
            }
        }
        return chars.concatToString()
    }

    //-------------Retrofit
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status
    private val _property = MutableLiveData<MyProperty>()
    val property: LiveData<MyProperty>
        get() = _property
    private val _texto = MutableLiveData<String>()
    val texto: LiveData<String>
        get() = _texto

    private fun getEstateProperties() {
        viewModelScope.launch {
            try {
                val listResult = MyObject.retrofitService.getProperties()
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                if (listResult.isNotEmpty()) {
                    _property.value = listResult.random()
                    _texto.value = property.value.toString()
                    _word_secret.value = _texto.value
                    Log.i("Palabra oculta:::", _word_secret.value.toString())
                    _word_show.value = replaceLetter(_word_secret.value.toString())
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    //---
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
    private val listDraw by lazy {
        listOf(
            R.drawable.ahorcado0,
            R.drawable.ahorcado1,
            R.drawable.ahorcado2,
            R.drawable.ahorcado3,
            R.drawable.ahorcado4,
            R.drawable.ahorcado5,
            R.drawable.ahorcado6
        )
    }
    private var charsExist = ArrayList<Char>()//list of characters representing attempts

    init {
        getEstateProperties()
        _lives.value = 0
        _countOk.value = 0
        _live_draw.value = listDraw[0]
        _status_game.value = "jugando"
    }

    private lateinit var binding: GameFragmentBinding
    private fun replaceCheckLetter(str: String, myChar: Char): String {
        val chars: CharArray = str.toCharArray()
        if (!chars.contains(myChar.uppercaseChar()) && !chars.contains(myChar.lowercaseChar())) {
            _lives.value = (_lives.value)?.plus(1)
            _live_draw.value = listDraw[_lives.value?.toInt()!!]
            fail+=1
        } else {
            if (!charsExist.contains(myChar)) {
                charsExist.add(myChar)
                _countOk.value = (_countOk.value)?.plus(1)
                ok+=1
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
        val letter = entrada_letra.value.toString()[0]
        val result: String = replaceCheckLetter(_word_secret.value.toString(), letter)
        _word_show.value = result
        _live_draw.value = listDraw[_lives.value?.toInt()!!]
        if (result == _word_secret.value.toString()) {
            _status_game.value = "ganaste"
        }
        if (_lives.value == 6) {
            _status_game.value = "perdiste"
        }
    }
}
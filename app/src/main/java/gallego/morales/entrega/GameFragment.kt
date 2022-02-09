package gallego.morales.entrega

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gallego.morales.entrega.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        // Get the viewmodel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access to all of the
        // data in the VieWModel
        binding.gameViewModel = viewModel

        /** Setting up LiveData observation relationship **/
        viewModel.word_show.observe(viewLifecycleOwner, Observer { newWord ->
            binding.palabraOculta.text = newWord
        })

        viewModel.live_draw.observe(viewLifecycleOwner, Observer { changeImage ->
            binding.imagenJugador.setImageResource(changeImage)
        })

        viewModel.status_game.observe(viewLifecycleOwner, Observer { estadoPartida ->
            var status = estadoPartida
            if (status == "ganaste") {
                findNavController().navigate(R.id.action_gameFragment_to_winFragment)
            }
            if (status == "perdiste") {
                findNavController().navigate(R.id.action_gameFragment_to_loseFragment)
            }

        })


        return binding.root

    }

}
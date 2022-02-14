package gallego.morales.entrega.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gallego.morales.entrega.R
import gallego.morales.entrega.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var mediaPlayer: MediaPlayer
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
companion object{
    var nombre =""
}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.playGameButton.setOnClickListener {
            mediaPlayer.stop()
             nombre=binding.editTextTextPersonName.text.toString()
            findNavController().navigate(R.id.action_nav_home_to_gameFragment)
        }

        mediaPlayer = MediaPlayer.create(this.context, R.raw.music)
        mediaPlayer.isLooping = true;
        mediaPlayer.start()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
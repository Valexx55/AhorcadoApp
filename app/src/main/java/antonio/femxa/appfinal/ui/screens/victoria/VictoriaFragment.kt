package antonio.femxa.appfinal.ui.screens.victoria

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentVictoriaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VictoriaFragment : Fragment() {

    private lateinit var binding: FragmentVictoriaBinding
    private val viewModel: VictoriaViewModel by viewModels()

    private lateinit var navController: NavController

    private val args: VictoriaFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                navigateToCategoria()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVictoriaBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            binding.imagenVictoria.setBackgroundResource(R.drawable.progress_animation_gameover2)
            (binding.imagenVictoria.background as AnimationDrawable).start()
        } else {
            binding.imagenVictoria.setBackgroundResource(R.drawable.pantallavictoria)
        }

        binding.textPalabraOculta.text = args.palabra

        binding.btnInicio.setOnClickListener { navigateToCategoria() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.playSoundAsync(R.raw.sonido_ganador)
    }

    private fun navigateToCategoria() {
        navController.popBackStack(R.id.categoriaFragment, false)
    }
}

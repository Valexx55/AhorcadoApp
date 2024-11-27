package antonio.femxa.appfinal.ui.screens.derrota

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
import antonio.femxa.appfinal.databinding.FragmentDerrotaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DerrotaFragment : Fragment() {

    private lateinit var binding: FragmentDerrotaBinding
    private val viewModel: DerrotaViewModel by viewModels()

    private lateinit var navController: NavController

    private val args: DerrotaFragmentArgs by navArgs()

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
        binding = FragmentDerrotaBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            binding.imagenDerrota.setBackgroundResource(R.drawable.progress_animation_gameover)
            (binding.imagenDerrota.background as AnimationDrawable).start()
        } else {
            binding.imagenDerrota.setBackgroundResource(R.drawable.pantallagameover)
        }

        binding.textPalabraOculta.text = args.palabra

        binding.btnInicio.setOnClickListener { navigateToCategoria() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.playSoundAsync(R.raw.sonido_perdedor)
    }

    private fun navigateToCategoria() {
        navController.popBackStack(R.id.categoriaFragment, false)
    }
}

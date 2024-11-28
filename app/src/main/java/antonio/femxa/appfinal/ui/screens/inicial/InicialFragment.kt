package antonio.femxa.appfinal.ui.screens.inicial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentInicialBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InicialFragment : Fragment() {

    private lateinit var binding: FragmentInicialBinding
    private val viewModel: InicialViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicialBinding.inflate(inflater, container, false)

        binding.btnJugar.setOnClickListener {
            navController.navigate(R.id.categoriaFragment)
        }

        binding.btnSonido.setOnClickListener {
            viewModel.toggleSonido()
        }

        binding.btnCreditos.setOnClickListener {
            navController.navigate(R.id.creditosFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicaOnOff().collect { isOn ->
                    if (isOn) {
                        binding.btnSonido.text = getString(R.string.sonido_on)
                        viewModel.playSongOrContinue(R.raw.inicio1)
                    } else {
                        binding.btnSonido.text = getString(R.string.sonido_off)
                    }
                }
            }
        }

        return binding.root
    }

}

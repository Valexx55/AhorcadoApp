package antonio.femxa.appfinal.ui.screens.inicial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentInicialBinding
import dagger.hilt.android.AndroidEntryPoint

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

        // TODO: Implement sound
        binding.btnSonido.setOnClickListener {
            Toast.makeText(context, "Not implemented", Toast.LENGTH_LONG).show()
        }

        binding.btnCreditos.setOnClickListener {
            navController.navigate(R.id.creditosFragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.playSongOrContinue(R.raw.inicio1)
    }
}

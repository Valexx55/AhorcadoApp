package antonio.femxa.appfinal.ui.screens.categoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentCategoriaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriaFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCategoriaBinding
    private val viewModel: CategoriaViewModel by viewModels()

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
        binding = FragmentCategoriaBinding.inflate(inflater, container, false)

        binding.spinnerCategorias.setSelection(0)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategorias.adapter = adapter
        binding.spinnerCategorias.setSelection(0)
        binding.spinnerCategorias.onItemSelectedListener = this

        binding.btnSonido.setOnClickListener {
            viewModel.toggleSonido()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicaOnOff().collect { isOn ->

                    if (isOn) {
                        viewModel.playSongOrContinue(R.raw.inicio)
                        binding.btnSonido.setImageResource(R.drawable.ic_volume_up)
                    } else {
                        binding.btnSonido.setImageResource(R.drawable.ic_volume_off)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (pos == 0) return

        val action = CategoriaFragmentDirections.toTableroFragment(
            categoria = pos
        )

        binding.spinnerCategorias.setSelection(0)

        navController.navigate(action)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}

package antonio.femxa.appfinal.ui.screens.categoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentCategoriaBinding
import dagger.hilt.android.AndroidEntryPoint

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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.playSongOrContinue(R.raw.inicio)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (pos == 0) return

        val arrayCategorias = resources.obtainTypedArray(R.array.array_categorias)
        val categoria = binding.spinnerCategorias.selectedItem.toString()
        val palabra = arrayCategorias.getTextArray(pos).random().toString()

        arrayCategorias.recycle()

        val action = CategoriaFragmentDirections.toTableroFragment(
            categoria = categoria,
            palabra = palabra
        )

        binding.spinnerCategorias.setSelection(0)

        navController.navigate(action)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}

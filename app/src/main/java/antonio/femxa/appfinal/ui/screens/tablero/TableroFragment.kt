package antonio.femxa.appfinal.ui.screens.tablero

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentTableroBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TableroFragment : Fragment() {

    private lateinit var binding: FragmentTableroBinding
    private val viewModel: TableroViewModel by viewModels()

    private val args: TableroFragmentArgs by navArgs()

    private lateinit var navController: NavController

    private lateinit var adapter: FilaTableroRvAdapter

    private val imgsAhorcado = intArrayOf(
        R.drawable.ic_cuerda,
        R.drawable.ic_cabeza,
        R.drawable.ic_cuerpo,
        R.drawable.ic_brazo,
        R.drawable.ic_brazos,
        R.drawable.ic_pierna
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        adapter = FilaTableroRvAdapter(listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableroBinding.inflate(inflater, container, false)

        binding.tvCategoria.text = resources.getStringArray(R.array.categorias)[args.categoria]

        binding.btnSonido.setOnClickListener {
            viewModel.toggleSound()
        }

        binding.rvTablero.apply {
            adapter = this@TableroFragment.adapter
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.FLEX_START
                alignItems = AlignItems.CENTER
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }

        val alfabeto = resources.getStringArray(R.array.alfabeto).map { it[0] }.toTypedArray()

        binding.rvTeclado.apply {
            adapter = TecladoRvAdapter(alfabeto) { letra ->
                viewModel.tryLetter(letra)
            }
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.FLEX_START
                alignItems = AlignItems.CENTER
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.palabraFlow.collect { uiState ->
                    when (uiState) {
                        is TableroUiState.Waiting -> {}

                        is TableroUiState.Correct -> adapter.updateList(uiState.palabra)

                        is TableroUiState.Fail -> {
                            binding.ivAhorcado.setImageResource(
                                when (uiState.failCount) {
                                    in 1..5 -> imgsAhorcado[uiState.failCount]
                                    else -> {
                                        Log.wtf("TableroFragment", "Error en el contador de fallos")
                                        throw IllegalStateException("No debería llegar aquí")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicaOnOff().collect { isOn ->
                    if (isOn) {
                        viewModel.playSongOrContinue(R.raw.durantejugar)
                        binding.btnSonido.setImageResource(R.drawable.ic_volume_up)
                    } else {
                        binding.btnSonido.setImageResource(R.drawable.ic_volume_off)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startGame(
            context = requireContext(),
            categoria = args.categoria,
            onWin = { palabra ->
                val action = TableroFragmentDirections.toVictoriaFragment(
                    palabra = palabra
                )
                navController.navigate(action)
            },
            onLose = { palabra ->
                val action = TableroFragmentDirections.toDerrotaFragment(
                    palabra = palabra
                )
                navController.navigate(action)
            }
        )
    }

}

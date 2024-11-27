package antonio.femxa.appfinal.ui.screens.creditos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import antonio.femxa.appfinal.R
import antonio.femxa.appfinal.databinding.FragmentCreditosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditosFragment : Fragment() {

    private lateinit var binding: FragmentCreditosBinding
    private val viewModel: CreditosViewModel by viewModels()

    private lateinit var adapter: CreditosRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditosBinding.inflate(inflater, container, false)

        adapter = CreditosRvAdapter(viewModel.parseCredits(resources.getXml(R.xml.creditos)))

        binding.rvCreditos.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = this@CreditosFragment.adapter
        }

        binding.btnHonduras.setOnLongClickListener {
            viewModel.playSoundAsync(R.raw.soni)
            true
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.playSongOrContinue(R.raw.inicio1)
    }
}

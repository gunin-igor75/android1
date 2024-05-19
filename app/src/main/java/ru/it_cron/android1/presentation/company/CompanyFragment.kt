package ru.it_cron.android1.presentation.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.R
import ru.it_cron.android1.constant.PN_FACEBOOK
import ru.it_cron.android1.constant.PN_INSTAGRAM
import ru.it_cron.android1.constant.PN_TELEGRAM
import ru.it_cron.android1.constant.URL_FACEBOOK
import ru.it_cron.android1.constant.URL_INSTAGRAM
import ru.it_cron.android1.constant.URL_TELEGRAM
import ru.it_cron.android1.databinding.FragmentCompanyBinding
import ru.it_cron.android1.presentation.extension.sendRequest

class CompanyFragment : Fragment() {
    private var _binding: FragmentCompanyBinding? = null
    private val binding: FragmentCompanyBinding
        get() = _binding ?: throw IllegalStateException("FragmentCompanyBinding is null")

    private val router: Router by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        onClickMenuListener()
        onClickBack()
    }

    private fun setupMenu() {
        binding.tbCompany.inflateMenu(R.menu.fragment_company)
    }

    private fun onClickMenuListener() {
        binding.tbCompany.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mItCron -> {
                    true
                }

                R.id.mInstagram -> {
                    sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
                    true
                }

                R.id.mFacebook -> {
                    sendRequest(URL_FACEBOOK, PN_FACEBOOK)
                    true
                }

                R.id.mTelegram -> {
                    sendRequest(URL_TELEGRAM, PN_TELEGRAM)
                    true
                }

                else -> false
            }
        }
    }

    private fun onClickBack() {
        val toolBar = binding.tbCompany
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}
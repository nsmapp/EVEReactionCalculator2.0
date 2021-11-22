package by.nepravsky.sm.evereactioncalculator.presentation.settings.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.nepravsky.sm.evereactioncalculator.databinding.FragmentAboutBinding
import by.nepravsky.sm.evereactioncalculator.BuildConfig
import by.nepravsky.sm.evereactioncalculator.utils.getAppName


class AboutFragment : Fragment() {

    lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAppName.text = "${requireContext().getAppName()} v${BuildConfig.VERSION_NAME}"
        binding.tvGit.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nsmapp"))
            startActivity(urlIntent)
        }

        binding.tvMarketerInfo.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://evemarketer.com/"))
            startActivity(urlIntent)
        }

        binding.tvMyEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:nsmappinfo@gmail.com")
            intent.putExtra(Intent.EXTRA_EMAIL, "nsmappinfo@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "app")
            startActivity(intent)
        }


    }


}
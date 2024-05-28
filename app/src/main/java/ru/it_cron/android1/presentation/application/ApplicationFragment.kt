package ru.it_cron.android1.presentation.application

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.BottomSheetAppBinding
import ru.it_cron.android1.databinding.FragmentApplicationBinding
import ru.it_cron.android1.domain.model.app.ContainerApp
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.model.app.StateScreenApp
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.application.PersonalInfoFragment.Companion.FLAG_SELECTION_PI
import ru.it_cron.android1.presentation.application.PersonalInfoFragment.Companion.KEY_FRAGMENT_PI
import ru.it_cron.android1.presentation.application.PoliticFragment.Companion.FLAG_SELECTION_PP
import ru.it_cron.android1.presentation.application.PoliticFragment.Companion.KEY_FRAGMENT_PP
import ru.it_cron.android1.presentation.application.adapters.ApplicationAdapter
import ru.it_cron.android1.presentation.application.adapters.FileAdapter
import ru.it_cron.android1.presentation.application.watchers.TextWatcherPhone
import ru.it_cron.android1.presentation.application.watchers.TextWatcherSimple
import ru.it_cron.android1.presentation.extension.callPhone
import ru.it_cron.android1.presentation.extension.getExtension
import ru.it_cron.android1.presentation.utils.getColorIdFile
import ru.it_cron.android1.presentation.utils.getMimeType
import ru.it_cron.android1.presentation.utils.makeLinks
import java.io.File
import java.util.UUID


class ApplicationFragment : Fragment() {

    private var _binding: FragmentApplicationBinding? = null
    private val binding: FragmentApplicationBinding
        get() = _binding ?: throw IllegalStateException("FragmentApplicationBinding is null")

    private val router: Router by inject()

    private val viewModel by viewModel<ApplicationViewModel>()

    private val serviceAdapter by lazy {
        ApplicationAdapter()
    }
    private val budgetAdapter by lazy {
        ApplicationAdapter()
    }
    private val areaActivityAdapter by lazy {
        ApplicationAdapter()
    }

    private val fileItemAdapter by lazy {
        FileAdapter(requireContext())
    }
    private lateinit var launcherContractFile: ActivityResultLauncher<String>
    private lateinit var launcherContractPhoto: ActivityResultLauncher<Uri>
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val uriImage: Uri by lazy {
        initTempUri()
    }

    private var requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launcherContractPhoto.launch(uriImage)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.grant_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        downloadFileFromPhone()
        downloadPhotoFromPhone()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickBack()
        createSpannableWordPhone()
        createSpannableWordPersonalInfo()
        createSpannableWordPolitic()
        setupRecyclerViewServicesBudgetAreaActivity()
        onClickAdaptersItem()
        setupEditTextTask(
            binding.inTask.tilTask,
            binding.inTask.tetTask
        )
        setupEditTextNameAndCompany(
            binding.inContactsInfo.tilName,
            binding.inContactsInfo.tetName,
            true
        )
        setupEditTextNameAndCompany(
            binding.inContactsInfo.tilCompany,
            binding.inContactsInfo.tetCompany,
            false
        )
        setupEditTextEmail(
            binding.inContactsInfo.tilEmail,
            binding.inContactsInfo.tetEmail
        )
        setupEditTextPhone(
            binding.inContactsInfo.tilPhone,
            binding.inContactsInfo.tetPhone
        )
        launchBottomSheet()
        setCheckBoxState()
        onClickButtonSendApp()
        setupButtonAttachFile()
        launchWarningFileMaxSize()
        onClickButtonCloseWarningFileMaxSize()
        setupButtonSendApp()
        observeAnswerSendApp()
    }

    private fun launchCamera() {
        if (hasRequiredPermissions(CAMERA_PERMISSIONS)) {
            launcherContractPhoto.launch(uriImage)
        } else {
            requestPermissionLauncher.launch(CAMERA_PERMISSIONS[0])
        }
    }

    private fun downloadFileFromPhone() {
        val contract = ActivityResultContracts.GetContent()
        launcherContractFile = registerForActivityResult(contract) { uri ->
            val uriCurrent = uri ?: throw IllegalStateException("Uri is null")
            createFileItem(
                uri = uriCurrent,
                isPhoto = false
            )
        }
    }

    private fun downloadPhotoFromPhone() {
        val contract = ActivityResultContracts.TakePicture()
        launcherContractPhoto = registerForActivityResult(contract) { result ->
            try {
                if (result) {
                    createFileItem(
                        uri = uriImage,
                        isPhoto = true
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error ${e.message}")
            }
        }
    }

    private fun createFileItem(
        uri: Uri,
        isPhoto: Boolean,
    ) {
        val docFile = DocumentFile.fromSingleUri(requireContext(), uri)
        val nameFile = docFile?.name ?: ""
        val size = docFile?.length() ?: 0
        if (size > MAX_SIZE_FILE) {
            viewModel.isFileMaxSize(true)
            return
        }
        val extension = nameFile.getExtension()
        val mimeType = getMimeType(nameFile)
        requireActivity().contentResolver.openInputStream(uri).use {
            val byteArray = it?.readBytes()
            val fileItem = FileItem(
                id = UUID.randomUUID().toString(),
                nameFile = nameFile,
                size = size,
                extension = extension,
                mimeType = mimeType,
                colorId = getColorIdFile(nameFile),
                isPhoto = isPhoto
            )
            fileItem.byteArray = byteArray
            fileItem.uri = uri
            viewModel.addFileItem(fileItem)
        }
    }


    private fun launchBottomSheet() {
        binding.inTask.btAttachFile.setOnClickListener {
            bottomSheetDialog = BottomSheetDialog(
                requireActivity(), R.style.BottomSheetDialogTheme
            )
            val bsBinding = BottomSheetAppBinding.inflate(layoutInflater, null, false)
            bottomSheetDialog.setContentView(bsBinding.root)
            bottomSheetDialog.show()
            onClickViewBottomSheet(bsBinding) {
                bottomSheetDialog.cancel()
            }
        }
    }

    private fun onClickViewBottomSheet(
        binding: BottomSheetAppBinding,
        onClickCancel: () -> Unit,
    ) {
        binding.cvPhoto.setOnClickListener {
            launchCamera()
            bottomSheetDialog.cancel()
        }
        binding.cvDocument.setOnClickListener {
            launcherContractFile.launch("*/*")
            bottomSheetDialog.cancel()
        }
        binding.cvCancel.setOnClickListener {
            onClickCancel()
        }
    }

    private fun setupEditTextTask(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationText(text)
            viewModel.setTaskState(isValid)
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextNameAndCompany(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        isName: Boolean,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationText(text)
            if (isName) viewModel.setNameState(isValid) else viewModel.setCompanyState(isValid)
            if (isValid) {
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            }
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextEmail(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationEmailAndPhone(text, PATTERN_EMAIL)
            viewModel.setEmailState(isValid)
            if (isValid) {
                textInputLayout.error = null
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            } else {
                textInputLayout.error = getString(R.string.valid_email)
            }
        }
        textInputLayout.setErrorIconOnClickListener {
            textInputEditText.setText(getString(R.string.Empty))
            textInputLayout.error = null
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextPhone(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherPhone(
            editText = textInputEditText
        ) { text ->
            val isValid = validationEmailAndPhone(text, PATTERN_PHONE)
            viewModel.setPhoneState(isValid)
            if (isValid) {
                textInputLayout.error = null
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            } else {
                textInputLayout.error = getString(R.string.valid_number_phone)
            }
        }
        textInputLayout.setErrorIconOnClickListener {
            textInputEditText.setText(getString(R.string.Empty))
            textInputLayout.error = null
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }


    private fun setupRecyclerViewServicesBudgetAreaActivity() {
        binding.rvServices.adapter = serviceAdapter
        binding.rvBudgets.adapter = budgetAdapter
        binding.rvAreaActivity.adapter = areaActivityAdapter
        binding.inTask.rvFiles.adapter = fileItemAdapter

        viewModel.services.observe(viewLifecycleOwner) {
            serviceAdapter.submitList(it)
        }
        viewModel.budgets.observe(viewLifecycleOwner) {
            budgetAdapter.submitList(it)
        }
        viewModel.areaActivity.observe(viewLifecycleOwner) {
            areaActivityAdapter.submitList(it)
        }
        viewModel.fileItems.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.inTask.rvFiles.visibility = View.GONE
            } else {
                binding.inTask.rvFiles.visibility = View.VISIBLE
                fileItemAdapter.submitList(it)
            }
        }
    }

    private fun onClickAdaptersItem() {
        serviceAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleService(name)
                }
            }
        budgetAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleBudget(name)
                }
            }

        areaActivityAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleAreaActivity(name)
                }
            }
        fileItemAdapter.fileItemOnClickListener =
            object : FileAdapter.FileItemOnClickListener {
                override fun onClickItem(fileItem: FileItem) {
                    viewModel.deleteFileItem(fileItem)
                }
            }
    }

    private fun createSpannableWordPhone() {
        val view = binding.inBriefApp.tvAppPhone
        val listener = View.OnClickListener {
            callPhone(resources.getString(R.string.phone_number))
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.phrase_phone),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun createSpannableWordPersonalInfo() {
        val view = binding.chPersonalInfo
        val listener = View.OnClickListener {
            setFragmentResultListener(KEY_FRAGMENT_PI) { _, bundle ->
                binding.chPersonalInfo.isChecked = bundle.getBoolean(FLAG_SELECTION_PI)
            }
            router.navigateTo(Screens.openPersonalInfoFragment())
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.checkbox_app_span_1),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun createSpannableWordPolitic() {
        val view = binding.chPolitic
        val listener = View.OnClickListener {
            setFragmentResultListener(KEY_FRAGMENT_PP) { _, bundle ->
                binding.chPolitic.isChecked = bundle.getBoolean(FLAG_SELECTION_PP)
            }
            router.navigateTo(Screens.openPoliticFragment())
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.checkbox_app_span_2),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun setCheckBoxState() {
        binding.chPersonalInfo.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPersonalInfoState(isChecked)
        }
        binding.chPolitic.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPoliticState(isChecked)
        }
    }

    private fun onClickBack() {
        val toolBar = binding.tbApp
        toolBar.setNavigationOnClickListener {
            viewModel.clearChoices()
            router.exit()
        }
    }

    private fun validationText(text: String): Boolean {
        return text.isNotBlank()
    }

    private fun validationEmailAndPhone(email: String, pattern: String): Boolean {
        val regex = pattern.toRegex()
        return regex.matches(email)
    }

    private fun hasRequiredPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initTempUri(): Uri {
        val tempImageDir = File(
            requireContext().filesDir,
            resources.getString(R.string.temp_images_dir)
        )
        tempImageDir.mkdir()
        val fileName = UUID.randomUUID().toString() + ".jpeg"
        val tempImage = File(
            tempImageDir,
            fileName
        )
        return FileProvider.getUriForFile(
            requireContext(),
            resources.getString(R.string.authorities),
            tempImage
        )
    }

    private fun setupButtonSendApp() {
        lifecycleScope.launch {
            viewModel.buttonSendAppIsActive
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { isAppReady ->
                    binding.btSendApp.btSendApp.isEnabled = isAppReady
                    val alpha = if (isAppReady) ALPHA_FULL else ALPHA_HALF
                    binding.btSendApp.btSendApp.alpha = alpha
                }
        }
    }

    private fun onClickButtonSendApp() {
        binding.btSendApp.btSendApp.setOnClickListener {
            val containerApp = ContainerApp(
                task = binding.inTask.tetTask.text.toString().trim(),
                name = binding.inContactsInfo.tetName.text.toString().trim(),
                company = binding.inContactsInfo.tetCompany.text.toString().trim(),
                email = binding.inContactsInfo.tetEmail.text.toString().trim(),
                phone = binding.inContactsInfo.tetPhone.text.toString().trim()
            )
            viewModel.sendApp(containerApp)
        }
    }

    private fun setupButtonAttachFile() {
        viewModel.isFilesMaxCount.observe(viewLifecycleOwner) { isMaxCountFiles ->
            binding.inTask.btAttachFile.isEnabled = !isMaxCountFiles
            val alpha = if (isMaxCountFiles) ALPHA_HALF else ALPHA_FULL
            binding.inTask.btAttachFile.alpha = alpha
        }
    }

    private fun launchWarningFileMaxSize() {
        viewModel.isFileNaxSize.observe(viewLifecycleOwner) { isGone ->
            if (isGone) {
                binding.cvErrorSizeFile.visibility = View.VISIBLE
            } else {
                binding.cvErrorSizeFile.visibility = View.GONE
            }
        }
    }

    private fun onClickButtonCloseWarningFileMaxSize() {
        binding.btFileOk.btSendApp.setOnClickListener {
            viewModel.isFileMaxSize(false)
        }
    }

    private fun observeAnswerSendApp() {
        lifecycleScope.launch {
            viewModel.answer
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    when (state) {
                        StateScreenApp.Error -> {
                            router.navigateTo(Screens.openErrorAppFragment())
                        }

                        StateScreenApp.ErrorInternet -> {
                            router.navigateTo(Screens.openErrorFragment())
                        }

                        StateScreenApp.Success -> {
                            router.navigateTo(Screens.openSendAppOkFragment())
                        }
                    }
                }
        }
    }


    companion object {
        private const val PATTERN_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
        private const val PATTERN_PHONE = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}\$"
        private val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val TAG = "ApplicationFragment"
        private const val MAX_SIZE_FILE = 15_000_000
        private const val ALPHA_FULL = 1f
        private const val ALPHA_HALF = 0.5f

        @JvmStatic
        fun newInstance() = ApplicationFragment()
    }
}

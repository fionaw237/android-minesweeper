package com.example.android_minesweeper.screens.best_times

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.ConfirmDeleteAlertBinding
import com.example.android_minesweeper.models.AppDatabase
import com.example.android_minesweeper.databinding.HighScoresScreenBinding
import com.example.android_minesweeper.view_models.HighScoresViewModel

class HighScoresFragment : Fragment() {

    private lateinit var binding: HighScoresScreenBinding
    private lateinit var viewModel: HighScoresViewModel
    private lateinit var viewModelFactory: HighScoresViewModelFactory

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.show()

        binding = DataBindingUtil.inflate(inflater, R.layout.high_scores_screen, container, false)

        val dataSource =
            AppDatabase.getInstance(requireNotNull(this.activity).application).highScoreDao
        viewModelFactory = HighScoresViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HighScoresViewModel::class.java)
        binding.highScoresViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getBestTimes()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_best_times, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_best_times_button) {

            DataBindingUtil.inflate<ConfirmDeleteAlertBinding>(
                layoutInflater,
                R.layout.confirm_delete_alert,
                view as? ViewGroup,
                false
            ).also { binding ->
                AlertDialog.Builder(context).create().also { dialog ->
                    dialog.setView(binding.root)
                    dialog.setCancelable(false)
                    dialog.show()

                    binding.cancelDeleteButton.setOnClickListener {
                        dialog.cancel()
                    }
                    binding.confirmDeleteButton.setOnClickListener {
                        dialog.cancel()
                        viewModel.deleteBestTimesButtonPressed()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
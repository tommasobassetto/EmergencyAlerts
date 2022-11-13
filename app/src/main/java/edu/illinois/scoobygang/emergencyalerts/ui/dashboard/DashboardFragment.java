package edu.illinois.scoobygang.emergencyalerts.ui.dashboard;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.illinois.scoobygang.emergencyalerts.ContactSelectActivity;
import edu.illinois.scoobygang.emergencyalerts.MainActivity;
import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.SettingsActivity;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private final View.OnClickListener sendAlertClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ContactSelectActivity.class);
            startActivity(i);
        }
    };

    private final View.OnClickListener settingsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), SettingsActivity.class);
            startActivity(i);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // attach listeners to buttons
        Button sendAlertActivity = binding.sendAlertButton;
        sendAlertActivity.setOnClickListener(this.sendAlertClicked);

        Button settingsActivity = binding.settingsButton;
        settingsActivity.setOnClickListener(this.settingsClicked);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

// COMPLETED (4) Create SettingsFragment and extend PreferenceFragmentCompat
// COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment
public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {
  // COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference
  private void setPreferenceSummary (Preference p, Object obj) {
    String theValue = obj.toString();
    String key = p.getKey();

    if(p instanceof ListPreference) {
      ListPreference theList = (ListPreference) p;
      int theIndex = theList.findIndexOfValue(theValue);

      if(theIndex >= 0) {
        p.setSummary(theList.getEntries()[theIndex]);
      }
    } else {
      p.setSummary(theValue);
    }
  }

  // COMPLETED (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.settings_sunshine);

    // Do step 9 within onCreatePreference
    // COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference
    int prefCount = getPreferenceScreen().getPreferenceCount();
    for(int i=0; i < prefCount; i++) {
      Preference p = getPreferenceScreen().getPreference(i);

      if(!(p instanceof CheckBoxPreference)) {
        String val = getPreferenceScreen().getSharedPreferences().getString(p.getKey(), "");
        setPreferenceSummary(p, val);
      }
    }
  }

  // COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop
  @Override
  public void onStop() {
    super.onStop();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  // COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart
  @Override
  public void onStart() {
    super.onStart();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  // COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Preference p = findPreference(key);
    if(p != null) {
      if (!(p instanceof CheckBoxPreference)) {
        setPreferenceSummary(p, sharedPreferences.getString(key, ""));
      }
    }
  }
}

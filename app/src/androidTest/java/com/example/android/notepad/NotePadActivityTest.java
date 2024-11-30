package com.example.android.notepad;


import static org.junit.Assert.assertNotNull;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for the NotesList activity to ensure it launches correctly.
 */
@RunWith(AndroidJUnit4.class)
public class NotePadActivityTest {

    /**
     * Verifies that the NotesList activity launches properly.
     */
    @Test
    public void testActivityLaunches() {
        // Launch the NotesList activity
        try (ActivityScenario<NotesList> scenario = ActivityScenario.launch(NotesList.class)) {
            scenario.onActivity(activity -> {
                // Verify the activity is not null
                assertNotNull("Activity should be launched successfully", activity);
            });
        }
    }
}

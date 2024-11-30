// Generated by view binder compiler. Do not edit!
package com.example.android.notepad.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.android.notepad.NoteEditor;
import com.example.android.notepad.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NoteEditorBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final NoteEditor.LinedEditText note;

  private NoteEditorBinding(@NonNull LinearLayout rootView,
      @NonNull NoteEditor.LinedEditText note) {
    this.rootView = rootView;
    this.note = note;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NoteEditorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NoteEditorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.note_editor, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NoteEditorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.note;
      NoteEditor.LinedEditText note = ViewBindings.findChildViewById(rootView, id);
      if (note == null) {
        break missingId;
      }

      return new NoteEditorBinding((LinearLayout) rootView, note);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

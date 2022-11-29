package com.matthewfraser.cp470_losty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CustomDeleteDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Dialog dialog;
    public Button yesButton;
    private int postId; // id of post to delete
    PostDatabaseHelper db;
    private Context context;

    public CustomDeleteDialog(Activity activity, int postId) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.postId = postId;
        db = new PostDatabaseHelper(this.getContext());
        context = this.getContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_delete_dialog);
        yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesButton:
                // Run the delete post DB task asynchronously
                new AsyncDeletePostTask().execute();

                break;
            default:
                break;
        }
        dismiss();
    }

    private class AsyncDeletePostTask extends AsyncTask<Void, Void, Void> {
        private String resultStr = "";
        @Override
        protected Void doInBackground(Void... voids) {
            // Try to delete post from db
            if(db.deletePostById(postId)) {
                resultStr = "Successfully deleted post.";
            } else {
                resultStr = "Database error deleting post.";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            activity.finish();
            Toast.makeText(context, resultStr, Toast.LENGTH_LONG).show();
        }
    }



}

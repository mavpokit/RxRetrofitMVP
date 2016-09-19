package com.mavpokit.rxretrofitmvp.View.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    //private ArrayList<String> mDatasetTitle;
    //private ArrayList<String> mDatasetLink;
    private ListQuestion listQuestion =new ListQuestion();
    private IQuestionsPresenter presenter;

    public QuestionsAdapter(IQuestionsPresenter presenter) {
        this.presenter = presenter;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewTitle;
        public TextView mTextViewLink;
        public TextView mTextViewAnswers;
        public Context context;
        //public CardView mCard;


        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = (TextView)v.findViewById(R.id.textViewTitle);
            mTextViewLink = (TextView)v.findViewById(R.id.textViewLink);
            mTextViewAnswers = (TextView)v.findViewById(R.id.textViewAnswers);
            //mCard = (CardView)v.findViewById(R.id.card_view);answers_card_view
            context=v.getContext();
        }
    }

    public void setListQuestion(ListQuestion listQuestion) {
        this.listQuestion = listQuestion;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_questions_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextViewTitle.setText(mDatasetTitle.get(position));
//        holder.mTextViewLink.setText(mDatasetLink.get(position));
        holder.mTextViewTitle.setText(position+1+". "+ listQuestion.getItems().get(position).getTitle());
        String link=listQuestion.getItems().get(position).getLink();
        holder.mTextViewLink.setText(link.substring(7,link.length()));
        int answerCount=listQuestion.getItems().get(position).getAnswer_count();
        holder.mTextViewAnswers.setText("Answers:"+answerCount);
        //if (answerCount>0) holder.mTextViewAnswers.setBackgroundColor(Color.rgb(0xc1,0xff,0xc1));

        if (answerCount>0) holder.mTextViewAnswers.setBackgroundColor(holder.context.getResources().getColor(R.color.answered_background));

        else holder.mTextViewAnswers.setBackgroundColor(Color.rgb(0xd9,0xda,0xd9));

        holder.mTextViewLink.setOnClickListener(v ->  presenter.openLink(position));

        holder.mTextViewAnswers.setOnClickListener(v-> presenter.showAnswers(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listQuestion.getItems().size();
    }

    //only for joy
    public void addItems(ListQuestion listQuestion){
        this.listQuestion.getItems().addAll(0, listQuestion.getItems());
        notifyDataSetChanged();
    }
}
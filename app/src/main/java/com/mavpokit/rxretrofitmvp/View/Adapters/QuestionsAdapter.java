package com.mavpokit.rxretrofitmvp.View.Adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    //private ArrayList<String> mDatasetTitle;
    //private ArrayList<String> mDatasetLink;
    private ListQuestion listQuestion =new ListQuestion();
    private IQuestionsPresenter presenter;

    // Allows to remember the last item shown on screen for animation
    private int lastPosition = -1;

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
        public LinearLayout container;
        public Context context;
        //public CardView mCard;


        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = (TextView)v.findViewById(R.id.textViewTitle);
            mTextViewLink = (TextView)v.findViewById(R.id.textViewLink);
            mTextViewAnswers = (TextView)v.findViewById(R.id.textViewAnswers);
            container = (LinearLayout)v.findViewById(R.id.questions_item_container);
            //mCard = (CardView)v.findViewById(R.id.card_view);answers_card_view
            context=v.getContext();
        }

    }

    public void setListQuestion(ListQuestion listQuestion) {
        this.listQuestion = listQuestion;
        notifyDataSetChanged();
        lastPosition = -1;
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

//        if (answerCount>0) holder.mTextViewAnswers.setBackgroundColor(holder.context.getResources().getColor(R.color.answered_background));
//        else holder.mTextViewAnswers.setBackgroundColor(Color.rgb(0xd9,0xda,0xd9));

        if (answerCount>0) holder.mTextViewAnswers.setBackgroundDrawable(holder.context.getResources().getDrawable(R.drawable.button_shape_round));
        else holder.mTextViewAnswers.setBackgroundDrawable(holder.context.getResources().getDrawable(R.drawable.button_shape_round_0answers));

        holder.mTextViewLink.setOnClickListener(v ->  presenter.openLink(position));

        holder.mTextViewTitle.setOnClickListener(v-> presenter.showAnswers(position));
        holder.mTextViewAnswers.setOnClickListener(v-> presenter.showAnswers(position));

//        setAnimation(holder.mTextViewLink, position);
//        setAnimation(holder.mTextViewAnswers, position);
        setAnimation(holder.container, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
//view animation
//            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
//            Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            Animation animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//            Animation animation = new AlphaAnimation(0,1);
//            animation.setDuration(500);
//            viewToAnimate.startAnimation(animation);

//property animation
//            ObjectAnimator animation = ObjectAnimator.ofFloat(viewToAnimate,View.SCALE_X,0,1);
            ObjectAnimator animation = ObjectAnimator.ofFloat(viewToAnimate,View.X,-100,0);
            animation.setDuration(200);
            animation.start();
//            viewToAnimate.animate().rotationYBy(720).alpha(0.5f);

            lastPosition = position;
        }
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

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        //super.onViewDetachedFromWindow(holder);
        ((ViewHolder)holder).container.clearAnimation();

    }
}
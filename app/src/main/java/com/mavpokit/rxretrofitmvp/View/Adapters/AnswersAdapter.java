package com.mavpokit.rxretrofitmvp.View.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.R;

/**
 * Created by Alex on 04.08.2016.
 */
public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>{
    private ListAnswer listAnswer =new ListAnswer();
    private IAnswersPresenter presenter;

    // Allows to remember the last item shown on screen for animation
    private int lastPosition = -1;

    public AnswersAdapter(IAnswersPresenter presenter) {
        this.presenter = presenter;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewAnswer;


        public ViewHolder(View v) {
            super(v);
            mTextViewAnswer = (TextView)v.findViewById(R.id.answerItem_textViewAnswer);
            //mCard = (CardView)v.findViewById(R.id.card_view);
        }
    }

    public void setListAnswer(ListAnswer listAnswer) {
        this.listAnswer = listAnswer;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_answers_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextViewAnswer.setText(Html.fromHtml( listAnswer.getItems().get(position).getBody()));
        setAnimation(holder.mTextViewAnswer, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listAnswer.getItems().size();
    }

}


//only for joy
//    public void addItems(ListAnswer listAnswer){
//        this.listAnswer.getItems().addAll(0, listAnswer.getItems());
//        notifyDataSetChanged();
//    }

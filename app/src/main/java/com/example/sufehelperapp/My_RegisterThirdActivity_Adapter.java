package com.example.sufehelperapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class My_RegisterThirdActivity_Adapter extends RecyclerView.Adapter<My_RegisterThirdActivity_Adapter.ViewHolder> {

    private Context mContext;
    private List<My_RegisterThirdActivity_Interal2> mInteralList;
    private static final int ITEM_HEAD = 3;
    private static final int ITEM_FOOT = 4;
    private static final int ITEM_ONE  = 1;
    private static final int ITEM_TWO  = 2;

    private int headViewCount = 1;
    private int footViewCount = 1;
/*
    //private OnItemClickListener onItemClickListener;
    private ButtonInterface buttonInterface;

    public My_RegisterThirdActivity_Adapter(Context context, List<My_RegisterThirdActivity_Interal2> interalList) {
        this.mInteralList = interalList;
        this.mContext = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_my_register_third_interal, parent,false);
            return new HeaderViewHolder(view);
        } else if(viewType == ITEM_FOOT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_my_register_third_interal3, parent,false);
            return new FootViewHolder(view);
        } else if (viewType == ITEM_ONE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_my_register_third_interal2, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ITEM_TWO) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_my_register_third_interal2, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(holder instanceof HeaderViewHolder) {
            //监听spinner内容变化
            ((HeaderViewHolder) holder).dormAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    String[] areas = getResources().getStringArray(R.array.area);
                    area = areas[pos];
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            ((HeaderViewHolder) holder).dormNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    String [] dormNames = getResources().getStringArray(R.array.dormitory);
                    dormName = dormNames[pos];
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });



        } else if (holder instanceof FootViewHolder) {

            ((FootViewHolder) holder).mButton8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonInterface != null) {
                        buttonInterface.onButtonClick(v);
                    }
                }
            });
        } else if (holder instanceof ViewHolder) {
            holder.itemView.setTag(position-1);
            ((ViewHolder) holder).interalName.setText(mInteralList.get(position-1).getName());

            ((ViewHolder) holder).mPerson1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonInterface != null) {
                        buttonInterface.onButtonClick(view);
                    }
                }
            });
            ((ViewHolder) holder).mPerson2.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonInterface != null) {
                        buttonInterface.onButtonClick(view);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeadView(position)) {
            return ITEM_HEAD;
        }
        if(isFootView(position)) {
            return ITEM_FOOT;
        }
        if(position % 2 == 0) {
            return ITEM_ONE;
        } else {
            return ITEM_TWO;
        }
    }
    @Override
    public int getItemCount() {
        return mInteralList.size()+headViewCount+footViewCount;
    }

    public boolean isHeadView(int position) {
        return headViewCount != 0 && position < headViewCount;
    }
    public boolean isFootView(int position) {
        return footViewCount != 0 && position >= (mInteralList.size()+headViewCount);
    }

    /*public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }*/
/*
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView interalName;
         final Button mPerson1;
         final Button mPerson2;

        public ViewHolder(View view) {
            super(view);
            interalName = (TextView) view.findViewById(R.id.interal2_name);
            mPerson1 = (Button) view.findViewById(R.id.person1);
            mPerson2 = (Button) view.findViewById(R.id.person2);
        }
    }

    static class HeaderViewHolder extends My_RegisterThirdActivity_Adapter.ViewHolder {
        //TODO: 获得实例
        Spinner dormAreaSpinner;
        Spinner dormNameSpinner;
        public HeaderViewHolder(View view) {

            super(view);
            //获取组件实例
            dormAreaSpinner = (Spinner) view.findViewById(R.id.spinner_address1);
            dormNameSpinner = (Spinner) view.findViewById(R.id.spinner_address2);
        }
    }

    static class FootViewHolder extends  My_RegisterThirdActivity_Adapter.ViewHolder {
        Button mButton8;
        public FootViewHolder(View view) {
            super(view);
            mButton8 = (Button) view.findViewById(R.id.button_confirm);
        }
    }*/

    /*public void setOnItemClickListener(OnItemClickListener onItemClickListener){
         this.onItemClickListener = onItemClickListener;
    }*/

    /*
    public void setButtonInterface(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
    }
    public interface ButtonInterface {
        void onButtonClick(View view);
    }*/
    //定义一个接口，利用接口回调
    /*public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }*/

}

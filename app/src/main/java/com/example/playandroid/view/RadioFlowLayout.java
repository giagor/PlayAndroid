package com.example.playandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.MarginModel;
import com.example.playandroid.entity.TagModel;

/**
 * 单选流式布局.
 * */
public class RadioFlowLayout extends RadioGroup {
    /**
     * item项的高.
     * */
    private float itemHeight;

    /**
     * item的列与列间的间隔.
     * */
    private float itemSpace;
    
    /**
     * item的行与行间的间隔.
     * */
    private float dividerHeight;
    
    public RadioFlowLayout(Context context) {
        super(context);
    }

    public RadioFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取xml布局中的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.flowlayout);
        itemHeight = typedArray.getDimension(R.styleable.flowlayout_itemHeight,0);
        itemSpace = typedArray.getDimension(R.styleable.flowlayout_itemSpace,0);
        dividerHeight = typedArray.getDimension(R.styleable.flowlayout_dividerHeight,0);
        typedArray.recycle();
    }
    
    public float getItemHeight(){
        return itemHeight;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        
        //总宽度
        int width = 0;
        //总高度
        int height = 0;
        //item所处行数
        int lineIndex = 0;
        //行宽
        int lineWidth = 0;
        
        int marginLeft = 0;
        int marginTop = 0;
        
        //得到子控件的数目
        int childCount = getChildCount();
        
        if(childCount > 0){
            for(int i = 0;i<childCount;i++){
                
                View child  =getChildAt(i);
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                
                if(childWidth + itemSpace + lineWidth + getPaddingLeft() + getPaddingRight() > sizeWidth){
                    //换行之后
                    lineIndex ++;
                    marginLeft = 0;
                    lineWidth = childWidth;
                    marginTop += (itemHeight + dividerHeight);
                }else{
                    //还是在原先的一行
                    lineWidth += childWidth + itemSpace*(i>0?1:0);
                    marginLeft = lineWidth - childWidth;
                }
                
                int left = marginLeft + getPaddingLeft();
                int right = marginLeft + getPaddingRight();
                int top = marginTop + getPaddingTop();
                int bottom = (int) (marginTop + itemHeight + getPaddingTop());

                TagModel tagModel;
                Object object = child.getTag();
                @SuppressLint("DrawAllocation") 
                MarginModel marginModel = new MarginModel(left,top,right,bottom);
                
                if(object instanceof TagModel){
                    tagModel = (TagModel) object;
                }else{
                    tagModel = new TagModel();
                }
                //通过tag的方式来储存子View的位置信息
                tagModel.setMarginModel(marginModel);
                child.setTag(tagModel);
            }
            height = (int) (itemHeight*(lineIndex+1) + dividerHeight*lineIndex +getPaddingTop()+getPaddingBottom());
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth:width,
                modeHeight==MeasureSpec.EXACTLY ? sizeHeight:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i = 0;i<getChildCount();i++){
            View view  = getChildAt(i);
            Object object = view.getTag();
            if(object instanceof TagModel){
                TagModel tagModel = (TagModel) object;
                MarginModel marginModel = tagModel.getMarginModel();
                if(marginModel != null){
                    view.layout(marginModel.getLeft(),marginModel.getTop(),marginModel.getRight(),
                            marginModel.getBottom());
                }
            }
                    
        }
    }
}

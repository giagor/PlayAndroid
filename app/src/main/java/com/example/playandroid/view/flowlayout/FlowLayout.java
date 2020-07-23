package com.example.playandroid.view.flowlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.FlowLayoutBean;
import com.example.playandroid.util.ApplicationContext;

/**
 * 自定义流式布局.
 */
public class FlowLayout extends LinearLayout {
    /**
     * item项的高.
     */
    private float mItemHeight;

    /**
     * item的列与列间的间隔.
     */
    private float mItemSpace;

    /**
     * item的行与行间的间隔.
     */
    private float mDividerHeight;
    
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取xml布局中的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.flowlayout);
        mItemHeight = typedArray.getDimension(R.styleable.flowlayout_itemHeight, 0);
        mItemSpace = typedArray.getDimension(R.styleable.flowlayout_itemSpace, 0);
        mDividerHeight = typedArray.getDimension(R.styleable.flowlayout_dividerHeight, 0);
        typedArray.recycle();
    }

    public float getItemHeight() {
        return mItemHeight;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

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

        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {

                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();

                if (childWidth + mItemSpace + lineWidth + getPaddingLeft() + getPaddingRight() > sizeWidth) {
                    //要换行
                    lineIndex++;
                    marginLeft = 0;
                    lineWidth = childWidth;
                    marginTop += (mItemHeight + mDividerHeight);
                } else {
                    //不换行
                    lineWidth += childWidth + mItemSpace * (i > 0 ? 1 : 0);
                    marginLeft = lineWidth - childWidth;
                }

                //子view的位置信息
                int left = marginLeft + getPaddingLeft();
                int right = marginLeft + childWidth + getPaddingRight();
                int top = marginTop + getPaddingTop();
                int bottom = (int) (marginTop + mItemHeight + getPaddingTop());

                TagModel tagModel;
                Object object = child.getTag();
                //位置信息的封装
                MarginModel marginModel = new MarginModel(left, top, right, bottom);

                if (object instanceof TagModel) {
                    tagModel = (TagModel) object;
                } else {
                    tagModel = new TagModel();
                }
                //通过tag的方式来储存子View的位置信息
                tagModel.setMarginModel(marginModel);
                child.setTag(tagModel);
            }
            //通过行数等来计算流式布局的总高度
            height = (int) (mItemHeight * (lineIndex + 1) + mDividerHeight * lineIndex + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(sizeWidth, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            Object object = view.getTag();
            if (object instanceof TagModel) {
                TagModel tagModel = (TagModel) object;
                MarginModel marginModel = tagModel.getMarginModel();
                if (marginModel != null) {
                    //子view位置的摆放
                    view.layout(marginModel.getLeft(), marginModel.getTop(), marginModel.getRight(),
                            marginModel.getBottom());
                }
            }
        }
    }

    /**
     * 创建子view
     * @param itemHeight 子项的高度
     * @param bean 子项View对应的实体类
     * @param layoutId 子view的布局
     */
    public static  <T extends TextView> View createChildView(int itemHeight, FlowLayoutBean bean, 
                                                             int layoutId) {
        //为子view加载布局
        LayoutInflater inflater = LayoutInflater.from(ApplicationContext.getContext());
        T view = (T) inflater.inflate(layoutId, null);
        //设置RadioButton的参数
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.height = itemHeight;
        view.setLayoutParams(lp);
        //添加view的时候，将HotWord信息setTag()
        TagModel<FlowLayoutBean> tagModel = new TagModel<>();
        tagModel.setT(bean);
        view.setTag(tagModel);
        view.setText(bean.getName());
        return view;
    }
}

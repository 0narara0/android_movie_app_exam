package com.narara.android_movie_app.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;

enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private boolean swipeBack = false;
    private ButtonsState buttonShowedState = ButtonsState.GONE;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private SwipeControllerActions buttonsActions = null;
    private static final float buttonWidth = 300;

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    public SimpleItemTouchHelperCallback(SwipeControllerActions buttonsActions) {
        this.buttonsActions = buttonsActions;
        mAdapter = null;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


    //================================================================================
    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY, final int actionState,
                                  final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL ||
                    event.getAction() == MotionEvent.ACTION_UP;
            if (swipeBack) {
                if (dX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                else if (dX > buttonWidth) buttonShowedState = ButtonsState.LEFT_VISIBLE;

                if (buttonShowedState != ButtonsState.GONE) {
                    setTouchDownListener(c, recyclerView, viewHolder,
                            dX, dY, actionState, isCurrentlyActive);
                    setItemsClickable(recyclerView, false);
                }
            }
            return false;
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY, final int actionState,
                                      final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive);
            }
            return false;
        });
    }


    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY, final int actionState,
                                    final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SimpleItemTouchHelperCallback.super.onChildDraw(c, recyclerView, viewHolder,
                        0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((v1, event1) -> false);
                setItemsClickable(recyclerView, true);
                swipeBack = false;

                if (buttonsActions != null && buttonInstance != null &&
                        buttonInstance.contains(event.getX(), event.getY())) {
                    if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                        buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
                    } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                        buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                    }
                }
                buttonShowedState = ButtonsState.GONE;
                currentItemViewHolder = null;
            }
            return false;
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas canvas, RecyclerView.ViewHolder viewHolder) {
        float buttonWidthWithoutPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint paint = new Paint();

        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(),
                itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        paint.setColor(Color.BLUE);
        canvas.drawRoundRect(leftButton, corners, corners, paint);
        drawText("EDIT", canvas, leftButton, paint);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding,
                itemView.getTop(), itemView.getRight(), itemView.getBottom());
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rightButton, corners, corners, paint);
        drawText("DELETE", canvas, rightButton, paint);

        buttonInstance = null;
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton;
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2),
                button.centerY() + (textSize / 2), p);
    }


    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }

}

/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark kd邻近算法，通过焦点获取点位值
 */
package com.mylove.happy.tv.focus;

public class FocusNode
{
    private Focus focus;
    private FocusNode left;
    private FocusNode right;
    private FocusNode parent;
    private int axis;

    public FocusNode(Focus focus)
    {
        this.focus = focus;
    }

    public Focus getFocus()
    {
        return focus;
    }

    public void setFocus(Focus focus)
    {
        this.focus = focus;
    }

    public FocusNode getLeft()
    {
        return left;
    }

    public void setLeft(FocusNode left)
    {
        this.left = left;
    }

    public FocusNode getRight()
    {
        return right;
    }

    public void setRight(FocusNode right)
    {
        this.right = right;
    }

    public int getAxis()
    {
        return axis;
    }

    public void setAxis(int axis)
    {
        this.axis = axis;
    }

    public FocusNode getParent()
    {
        return parent;
    }

    public void setParent(FocusNode parent)
    {
        this.parent = parent;
    }

    @Override 
    public String toString()
    {
        return "TreeNode{" +
                "position=" + focus.toString() +
                '}';
    }
}

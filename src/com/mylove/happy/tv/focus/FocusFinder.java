/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark kd邻近算法，通过焦点获取点位值
 */
package com.mylove.happy.tv.focus;

import java.util.*;

public class FocusFinder
{
    FocusNode root;
    int leafCount;
    double  minDistance=Integer.MAX_VALUE;
    int lastAxis=0;
    FocusNode nearestNode;
    Stack<FocusNode> backTrackingStack = new Stack<FocusNode>();

	public FocusFinder(List<Focus> focuses){
    	buildTree(focuses, getRoot(), -1);
    }
    
    public void buildTree(List<Focus> focuses, FocusNode root, int axis)
    {
    	 lastAxis = axis;
         if(focuses.size()==0)
         {
             return;
         }
         if(isXVarianceGreaterThanYVariance(focuses))
         {
             sortX(focuses);
             axis=0;
         }
         else
         {
             sortY(focuses);
             axis=1;
         }
         int middle = focuses.size()/2;
         Focus split = focuses.get(middle);
         if(getRoot()==null)
         {
             FocusNode newNode = new FocusNode(split);
             newNode.setAxis(axis);
             newNode.setParent(null);
             setRoot(newNode);
             root = getRoot();
             setLeafCount(1);
         }
         if (lastAxis == 0)
         {
             if(root.getLeft()==null)
             {
                 FocusNode newNode = new FocusNode(split);
                 newNode.setParent(root);
                 newNode.setAxis(axis);
                 root.setLeft(newNode);
                 setLeafCount(getLeafCount()+1);
                 if(focuses.size()==1)
                 {
                     //叶子节点不记录分割轴
                     root.getLeft().setAxis(-1);
                     return;
                 }
             }
             root = root.getLeft();
         }
         else if (lastAxis == 1)
         {
             if(root.getRight()==null)
             {
                 FocusNode newNode = new FocusNode(split);
                 newNode.setParent(root);
                 newNode.setAxis(axis);
                 root.setRight(newNode);
                 setLeafCount(getLeafCount()+1);
                 if(focuses.size()==1)
                 {
                     root.getRight().setAxis(-1);
                     return;
                 }
             }
             root = root.getRight();
         }
         List<Focus> left = focuses.subList(0,middle);
         List<Focus> right = focuses.subList(middle+1,focuses.size());
         buildTree(left,root,0);
         buildTree(right,root,1);
    }
    
    public Focus findNextFocus(Focus focus, FocusNode root)
    {
    	backTrackingStack.push(root);
        if(root.getAxis()==0)
        {
            if(root.getFocus().getX()>focus.getX())
            {
                if(root.getLeft()!=null)
                {
                    root = root.getLeft();
                    findNextFocus(focus,root);
                }
                else
                {
                    double distance = twoPointDistance(focus,root.getFocus());
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nearestNode = root;
                    }
                }
            }
            else
            {
                if(root.getRight()!=null)
                {
                    root = root.getRight();
                    findNextFocus(focus,root);
                }
                else
                {
                    double distance = twoPointDistance(focus,root.getFocus());
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nearestNode = root;
                    }
                }
            }
        }
        else
        {
            if(root.getFocus().getY()>focus.getY())
            {
                if(root.getLeft()!=null)
                {
                    root = root.getLeft();
                    findNextFocus(focus,root);
                }
                else
                {
                    double distance = twoPointDistance(focus,root.getFocus());
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nearestNode = root;
                    }
                }
            }
            else
            {
                if(root.getRight()!=null)
                {
                    root = root.getRight();
                    findNextFocus(focus,root);
                }
                else
                {
                    double distance = twoPointDistance(focus,root.getFocus());
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nearestNode = root;
                    }
                }
            }
        }
        backTracking(focus,backTrackingStack);
        return nearestNode.getFocus();
    }
    
    boolean isXVarianceGreaterThanYVariance(List<Focus> positionList)
    {
    	 double[] paramsX = new double[positionList.size()];
         double[] paramsY = new double[positionList.size()];
         for(int i=0;i<positionList.size();i++)
         {
             paramsX[i] = positionList.get(i).getX();
             paramsY[i] = positionList.get(i).getY();
         }
         double xVariance = getVariance(paramsX);
         double yVariance = getVariance(paramsY);

         return xVariance-yVariance>=0? true : false;
    }
    
    double getVariance(double[] params)
    {
    	int count = params.length;
        double sum=0;
        for(int i=0;i<count;i++)
        {
            sum = sum + params[i];
        }
        double avg = sum / count;
        double varianceSum=0;
        for(int i=0;i<count;i++)
        {
            double diff = params[i]-avg;
            varianceSum = varianceSum + Math.pow(diff,2);
        }
        double variance = varianceSum /count;
        return variance;
    }
    
    List<Focus> sortY(List<Focus> focuses)
    {
    	Collections.sort(focuses, new Comparator<Focus>() {
            @Override public int compare(Focus o1, Focus o2)
            {
                if(o1.getY()-o2.getY() > 0)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        });
        return focuses;
    }
    
    List<Focus> sortX(List<Focus> focuses)
    {
    	Collections.sort(focuses, new Comparator<Focus>() {
            @Override public int compare(Focus o1, Focus o2)
            {
                if(o1.getX()-o2.getX() > 0)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        });
        return focuses;
    }
    
    
    
    double twoPointDistance(Focus focuses1, Focus focuses2)
    {
    	 double x = focuses1.getX()-focuses2.getX();
         double y = focuses1.getY()-focuses2.getY();
         return Math.sqrt(x*x + y*y);
    }
    
    void backTracking(Focus focus , Stack<FocusNode> backTrackingStack)
    {
    	 while(backTrackingStack.size()>0)
         {
             FocusNode lastNode = backTrackingStack.pop();
             if(lastNode == null) return;
             //叶子节点axis为-1,不计算分割区域
             if(lastNode.getAxis()==-1)
             {
                 double dist = twoPointDistance(focus,lastNode.getFocus());
                 if(dist < minDistance)
                 {
                     minDistance = dist;
                     nearestNode = lastNode;
                 }
             }
             else
             {
                 //按x轴分割
                 if(lastNode.getAxis()==0)
                 {
                     double dist = twoPointDistance(focus,lastNode.getFocus());
                     if(dist < minDistance)
                     {
                         minDistance = dist;
                         nearestNode = lastNode;
                     }
                     //如果目标节点和该加点在分割轴方向上距离小于最小距离,则说明以目标节点为圆心,最小距离为半径
                     //的圆会横跨两个平面
                     if(Math.abs(lastNode.getFocus().getX()-focus.getX()) < minDistance)
                     {
                         //如果目标节点的x坐标小于回朔节点的x轴,则进入回朔节点的右平面,反之进入左平面
                         if(lastNode.getFocus().getX()-focus.getX() > 0)
                         {
                             backTrackingStack.push(lastNode.getRight());
                         }
                         else
                         {
                             backTrackingStack.push(lastNode.getLeft());
                         }
                     }
                 }
                 else
                 {
                     //Y轴方向同X轴
                     double dist = twoPointDistance(focus,lastNode.getFocus());
                     if(dist < minDistance)
                     {
                         minDistance = dist;
                         nearestNode = lastNode;
                     }
                     if(Math.abs(lastNode.getFocus().getY()-focus.getY()) < minDistance)
                     {
                         if(lastNode.getFocus().getY()-focus.getY() > 0)
                         {
                             backTrackingStack.push(lastNode.getRight());
                         }
                         else
                         {
                             backTrackingStack.push(lastNode.getLeft());
                         }
                     }
                 }
             }
         }
    }
    
    public int getLeafCount()
    {
        return leafCount;
    }

    public void setLeafCount(int leafCount)
    {
        this.leafCount = leafCount;
    }

    public FocusNode getRoot()
    {
        return root;
    }

    public void setRoot(FocusNode root)
    {
        this.root = root;
    }
    
}

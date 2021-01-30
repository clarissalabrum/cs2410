package com.example.trees;

import java.util.Random;

public class Tree {
    // overall attributes
    private int maxLength;
    private int minLength;
    private int maxAngle;
    private int minAngle;
    private int maxWidth;
    private Branch root;

    public Tree(int[] constraints, int x, int y){
        maxLength = constraints[0];
        minLength = constraints[1];
        maxAngle = constraints[2];
        minAngle = constraints[3];
        maxWidth = constraints[4];

        root = new Branch(x, y);
    }

    public class Branch {
        //branch attribute
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private int width;
        private Branch left;
        private Branch right;
        private Branch root;

        // create root branch
        private Branch(int x, int y){
            this.x1 = x;
            this.y1 = y;
            this.x2 = x;
            this.y2 = y - maxLength;
            this.width = maxWidth;
            this.root = null;

            //generate branches
            if(this.width -10 > 0) {
                this.left = new Branch(this.width - 10, this);
                this.right = new Branch(generateWidth(this.width - 10), this);
            }
        }

        // generate other branches
        private Branch(int width, Branch root){
            int length = getLength();
            double angle = getAngle();
            this.x1 = root.x2;
            this.y1 = root.y2;
            this.x2 = (int) (length * Math.cos(angle)) + x1;
            this.y2 = y1 - (int) (length * Math.sin(angle));
            this.width = width;
            this.root = root;

            if(this.width -10 > 0) {
                this.left = new Branch(this.width - 10, this);
                this.right = new Branch(generateWidth(this.width - 10), this);
            }
        }

        public int getX1() {
            return x1;
        }

        public int getY1() {
            return y1;
        }

        public int getX2() {
            return x2;
        }

        public int getY2() {
            return y2;
        }

        public boolean hasLeft(){
            return (left != null);
        }

        public boolean hasRight(){
            return (right != null);
        }

        public Branch getLeft() {
            return left;
        }

        public Branch getRight() {
            return right;
        }

        public int getWidth() {
            return width;
        }
    }

    //generate angle
    private double getAngle(){
        double random = Math.random();
        int angle = (int) (random *(maxAngle - minAngle) + minAngle + 90);
        return (angle * Math.PI / 180);
    }

    //generate length
    private int getLength(){
        double random = Math.random();
        return (int) (random * (maxLength - minLength) + minLength);
    }

    //generate trunk width
    private int generateWidth(int width){
        double random = Math.random();
        return (int) (random *(width));
    }

    public Branch getTree(){return root;}
}

package core;

import models.TestSubject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<TestSubject> testSubjects;
    private static Random RAND = new Random();
    private int move;
    private int populationCount;

    public static int GENERATION;

    public Population(int count) {
        this.populationCount = count;
        move = 0;
        GENERATION = 1;
        this.testSubjects = new ArrayList<>();
        for (int i = 0; i < populationCount; i++) {
            int[][] curDNA = new int[29][29];
            for (int j = 0; j < curDNA.length; j++) {
                for (int k = 0; k < curDNA.length; k++) {
                    curDNA[j][k] = RAND.nextInt(8) + 1;
                }
            }
            TestSubject curTestSubject = new TestSubject(curDNA);
            testSubjects.add(curTestSubject);
        }
    }

    public void tick() {
        if (move < 40) {
            for (TestSubject testSubject : testSubjects) {
                testSubject.tick();
            }
            move++;
        } else {
            for (TestSubject testSubject : testSubjects) {
                testSubject.evaluate();
            }
            List<TestSubject> nextGen = this.getNextGenerationKids();
            List<TestSubject> mutatedTestSubjects = this.mutate(nextGen);
            this.testSubjects.clear();
            for (TestSubject mutatedTestSubject : mutatedTestSubjects) {
                this.testSubjects.add(new TestSubject(mutatedTestSubject.getDNA()));
            }
            move = 0;
            GENERATION++;
        }
    }

    private List<TestSubject> mutate(List<TestSubject> matingPool) {
        List<TestSubject> mutatedTestSubjects = new ArrayList<>();
        for (TestSubject testSubject : matingPool) {
            testSubject.mutate();
            mutatedTestSubjects.add(new TestSubject(testSubject.getDNA()));
        }
        return mutatedTestSubjects;
    }

    private List<TestSubject> getNextGenerationKids() {
        List<TestSubject> matingPool = new ArrayList<>();
        testSubjects.stream().sorted((o1, o2) -> Integer.compare(o2.getFitness(), o1.getFitness())).limit(populationCount / 8 + 1).forEach(matingPool::add);

        List<TestSubject> nextGen = new ArrayList<>();

        boolean timeToFill = false;
        while (nextGen.size() < populationCount) {
            if (!timeToFill) {

                for (int i = 0; i < populationCount / 3; i++) {
                    nextGen.add(new TestSubject(matingPool.get(0).getDNA()));
                }
                for (int i = 0; i < populationCount / 5; i++) {
                    nextGen.add(new TestSubject(matingPool.get(1).getDNA()));
                }
                for (int i = 0; i < populationCount / 10; i++) {
                    nextGen.add(new TestSubject(matingPool.get(2).getDNA()));
                }
                for (int i = 0; i < populationCount / 10; i++) {
                    nextGen.add(new TestSubject(matingPool.get(3).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(4).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(5).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(6).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(7).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(8).getDNA()));
                }
                for (int i = 0; i < populationCount / 20; i++) {
                    nextGen.add(new TestSubject(matingPool.get(9).getDNA()));
                }
            } else {
                nextGen.add(new TestSubject(matingPool.get(RAND.nextInt(populationCount / 4 + 1)).getDNA()));
            }
        }

//        List<TestSubject> kids = new ArrayList<>();
//        while (kids.size() < 40) {
//            TestSubject parentOne = matingPool.get(RAND.nextInt(matingPool.size()));
//            TestSubject parentTwo = matingPool.get(RAND.nextInt(matingPool.size()));
//
//            int[][] newDNA = new int[29][29];
//
//            for (int i = 0; i < newDNA.length; i++) {
//                for (int j = 0; j < newDNA.length; j++) {
//                    int parentGene = RAND.nextInt(2);
//                    if (parentGene == 0) {
//                        newDNA[i][j] = parentOne.getDNA()[i][j];
//                    }
//
//                    if (parentGene == 1){
//                        newDNA[i][j] = parentTwo.getDNA()[i][j];
//                    }
//                }
//            }
//
//            TestSubject kid = new TestSubject(newDNA);
//
//            kids.add(kid);
//        }
        return nextGen;
    }

    public void render(Graphics g) {
        for (TestSubject testSubject : testSubjects) {
            testSubject.render(g);
        }
    }

    public List<TestSubject> getTestSubjects() {
        return testSubjects;
    }

    public void setTestSubjects(List<TestSubject> testSubjects) {
        this.testSubjects = testSubjects;
    }

    public static Random getRAND() {
        return RAND;
    }

    public static void setRAND(Random RAND) {
        Population.RAND = RAND;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}

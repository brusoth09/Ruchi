package com.ruchi.engine.sentimentEvaluvation;

public class Calculator {
	  /** Precision for exact matches. */
	  public float precision;

	  /** Recall for exact matches. */
	  public float recall;

	  /** F1 for exact matches. */
	  public float f1;

	  /** Precision for exact and partial matches. */
	  public float precisionLenient;

	  /** Recall for exact and partial matches. */
	  public float recallLenient;

	  /** F1 for exact and partial matches. */
	  public float f1Lenient;

	  /** Number of exact matches. */
	  public float correct;

	  /** Number of instances incorretly with predicted label. */
	  public float spurious;

	  /** Number of positive instances missed by the system. */
	  public float missing;

	  /**
	   * Number n for BDM computation, number of matches, instead of number of match
	   * bdm score.
	   */
	  public float BDMn = 0;

	  /** Number of only partial matches. */
	  public float partialCor;

	  /** Number of positive examples in key set. */
	  private float keySize;

	  /** Number of positive examples in the results. */
	  private float resSize;

	  /** Constructor. Set everything as zero. */
	  public Calculator() {
	    precision = 0;
	    recall = 0;
	    f1 = 0;
	    precisionLenient = 0;
	    recallLenient = 0;
	    f1Lenient = 0;
	    correct = 0;
	    spurious = 0;
	    missing = 0;
	    partialCor = 0;
	    BDMn = 0;
	  }

	  /** Compute the F-measures from the numbers. */
	  public void computeFmeasure() {
	    keySize = correct + partialCor + spurious;
	    resSize = correct + partialCor + missing;
	    if(BDMn > 0) {
	      keySize = BDMn + spurious;
	      resSize = BDMn + missing;
	    }
	    if((keySize) == 0)
	      precision = 0;
	    else precision = (float)correct / keySize;
	    if((resSize) == 0)
	      recall = 0;
	    else recall = (float)correct / resSize;
	    if((precision + recall) == 0)
	      f1 = 0;
	    else f1 = 2 * precision * recall / (precision + recall);
	    return;
	  }

	  /** Compute the lenient F-measures from the numbers. */
	  public void computeFmeasureLenient() {
	    keySize = correct + partialCor + spurious;
	    resSize = correct + partialCor + missing;
	    if(BDMn > 0) {
	      keySize = BDMn + spurious;
	      resSize = BDMn + missing;
	    }
	    if((keySize) == 0)
	      precisionLenient = 0;
	    else precisionLenient = (float)(correct + partialCor) / keySize;
	    if((resSize) == 0)
	      recallLenient = 0;
	    else recallLenient = (float)(correct + partialCor) / resSize;
	    if((precisionLenient + recallLenient) == 0)
	      f1Lenient = 0;
	    else f1Lenient =
	            2 * precisionLenient * recallLenient
	                    / (precisionLenient + recallLenient);
	    return;
	  }

	  /** Accumulate the F-measure data for computing overall results. */
	  public void add(Calculator anotherMeasure) {
	    this.correct += anotherMeasure.correct;
	    this.partialCor += anotherMeasure.partialCor;
	    this.missing += anotherMeasure.missing;
	    this.spurious += anotherMeasure.spurious;
	    this.precision += anotherMeasure.precision;
	    this.recall += anotherMeasure.recall;
	    this.f1 += anotherMeasure.f1;
	    this.precisionLenient += anotherMeasure.precisionLenient;
	    this.recallLenient += anotherMeasure.recallLenient;
	    this.f1Lenient += anotherMeasure.f1Lenient;
	    this.BDMn += anotherMeasure.BDMn;
	    return;
	  }

	  /** Compute the macro averaged results. */
	  public void macroAverage(int k) {
	    if(k > 0) {
	      this.correct /= k;
	      this.partialCor /= k;
	      this.missing /= k;
	      this.spurious /= k;
	      this.precision /= k;
	      this.recall /= k;
	      this.f1 /= k;
	      this.precisionLenient /= k;
	      this.recallLenient /= k;
	      this.f1Lenient /= k;
	      this.BDMn /= k;
	    } else {
	      System.out
	              .println("Warning: the macro averaged F measure cannot be done because the number of pairs is less than 1.");
	    }
	    return;
	  }

	  /** Print out the results. */
	  public String printResults() {
	    StringBuffer logMessage = new StringBuffer();
	    logMessage.append("  (correct, partial, spurious, missing)= (" + correct
	            + ", " + partialCor + ", " + spurious + ", " + missing + ");  ");
	    logMessage.append("(precision, recall, F1)= (" + precision + ", " + recall
	            + ", " + f1 + ");  ");
	    logMessage.append("Lenient: (" + (new Float(precisionLenient)) + ", "
	            + recallLenient + ", " + f1Lenient + ")\n");
	    return logMessage.toString();
	  }
	}
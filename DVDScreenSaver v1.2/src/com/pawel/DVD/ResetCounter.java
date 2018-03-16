package com.pawel.DVD;

public class ResetCounter {
	
	private WallHitLabel wallHitLabel;
	private CornerHitLabel cornerHitLabel;

	public ResetCounter(WallHitLabel wallHitLabel, CornerHitLabel cornerHitLabel) {
		this.wallHitLabel = wallHitLabel;
		this.cornerHitLabel = cornerHitLabel;
	}

	void reset() {
		wallHitLabel.setWallHitCount(0);
		cornerHitLabel.setCornerHitCount(0);
		wallHitLabel.setLabelText(wallHitLabel.getWallHitCount());
		cornerHitLabel.setLabelText(cornerHitLabel.getCornerHitCount());	
	}
}

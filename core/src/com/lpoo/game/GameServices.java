package com.lpoo.game;

public interface GameServices {

	void signIn();
	void signOut();

	boolean isSignedIn();

	void submitScore(int level, int score);
	void unlockAchievement(String achievementID);

	//gets the scores/achievements and displays them through google's default widget
	void showScores(int level);
	void showAchievements();

    String getGravityAchievementID();

    String getSpeedAchievementID();

    String getWaterAchievementID();
}
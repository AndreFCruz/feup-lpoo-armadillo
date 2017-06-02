package com.lpoo.game;

public interface GameServices {

	void signIn();
	void signOut();

	boolean isSignedIn();
    void rateGame();

	void submitScore(int score);
	void unlockAchievement(String achievementID);

	//gets the scores/achievements and displays them through google's default widget
	void showScores();
	void showAchievements();
}
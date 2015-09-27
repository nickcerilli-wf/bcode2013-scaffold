package team005;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.Upgrade;

public class RobotPlayer {
    public static void run(RobotController rc) {
        while (true) {
            try {
            	
            	//double power = rc.getTeamPower();
            	//double health = rc.getEnergon();
            	//double shields = rc.getShields();
            	
                if (rc.getType() == RobotType.HQ) {
                	int currentRound = Clock.getRoundNum();
                    if (rc.isActive()) {
                        // Spawn a soldier
                        Direction dir = Direction.values()[(int)(Math.random()*8)];
                        if (rc.canMove(dir) && currentRound % 7 == 0)
                            rc.spawn(dir);
                        if (rc.checkResearchProgress(Upgrade.PICKAXE) <= 25)
                        	rc.researchUpgrade(Upgrade.PICKAXE);
                        if(currentRound > 600)
                        	rc.researchUpgrade(Upgrade.NUKE);
                    }
                }
                else if (rc.getType() == RobotType.ARTILLERY) {
                    if (rc.isActive()) {
                        //rc.attackSquare(rc.getLocation());
                    }
                }
                else if (rc.getType() == RobotType.GENERATOR) {
                    if (rc.isActive()) {
                    	;
                    }
                }
                else if (rc.getType() == RobotType.MEDBAY) {
                    if (rc.isActive()) {
                    	;
                    }
                }
                else if (rc.getType() == RobotType.SHIELDS) {
                    if (rc.isActive()) {
                    	;
                    }
                }
                else if (rc.getType() == RobotType.SOLDIER) {
                    if (rc.isActive()) {
                    	MapLocation currentLocation = rc.getLocation(); 
                    	MapLocation enemyHQLocation = rc.senseEnemyHQLocation();
                    	Direction dir = currentLocation.directionTo(enemyHQLocation);
                    	
                    	MapLocation nextLocation = currentLocation.add(dir);
                    	
                    	if(rc.senseMine(nextLocation) == Team.NEUTRAL
                    			|| rc.senseMine(nextLocation) == Team.B)
                    		rc.defuseMine(nextLocation);
                    	
                    	else if (Math.random()<0.005 
							&& rc.hasUpgrade(Upgrade.PICKAXE)
							&& rc.senseMine(currentLocation) == null) {
								rc.layMine();
						} 
                    	else { 
							// Choose a random direction, and move that way if possible
							if(rc.canMove(dir)) {
								rc.move(dir);
								
							}
						}
                    }
                }
                else if (rc.getType() == RobotType.SUPPLIER) {
                    if (rc.isActive()) {
                    	;
                    }
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
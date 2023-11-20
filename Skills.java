
public class Skills { // contains all the skills in the game, acts like a skill book
    private int skillPoints;
    public void skillBook(String skill, int skillPoints)
    {
        this.skillPoints = skillPoints;
        switch(skill)
        {
            case "Uppercut": // 40% to stun the opponent, but only does ok dmg
                skillCheck(1);
                break;
            case "Parry": // prepare to counter the next attack, makes it so that if the enemy attacks while you're parrying, it does no damage
                skillCheck(1);
                break;
            case "Fireball": //fireball attack, doesn't do much dmg, but has DOT across two turns? just timestamp
                skillCheck(1);
                break;
            case "Chainsaw": // this attack requires investment with skill points, can do a lot if you "rev" (invest enough sp) it up enough, also does DOT
                break;
            case "Deus Ex Machina": // hc gun barrage lmao, it just does the BIG damage, but is costs a decent amount of sp
                skillCheck(3);
                break;
            case "Recover":  // simple move that restores 30% of your hp
                skillCheck(1);
                break;
        }
    }

    public boolean skillCheck(int cost)
    {
        if (skillPoints >= cost)
        {
            skillPoints -= cost;
            return true;
        }
        else
        {
            System.out.println("Not enough skill points!");
            return false;
        }
    }

    public int getSkillpoints()
    {
        return skillPoints;
    }
}

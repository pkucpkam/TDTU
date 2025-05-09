namespace Chapter.Strategy
public class BasicShield : IShieldBehaviour
{
    public float AbsorbDamage(float damage)
    {
        return damage * 0.8f;  
    }
}

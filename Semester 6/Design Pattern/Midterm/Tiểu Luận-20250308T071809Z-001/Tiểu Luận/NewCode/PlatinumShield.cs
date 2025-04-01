public class PlatinumShield : IShieldBehaviour
{
    public float AbsorbDamage(float damage)
    {
        return damage * 0.5f;  // Giảm 50% sát thương
    }
}

using UnityEngine;
namespace Chapter.Strategy

{
    public class ShieldItem : MonoBehaviour
    {
        public IShieldBehaviour shieldBehaviour; 

        void Start()
        {
            int randomShield = Random.Range(0, 3);  
            switch (randomShield)
            {
                case 0:
                    shieldBehaviour = new BasicShield();
                    break;
                case 1:
                    shieldBehaviour = new PlatinumShield();
                    break;
                case 2:
                    shieldBehaviour = new AbsoluteShield();
                    break;
            }
        }

        void OnTriggerEnter(Collider other)
        {
            if (other.CompareTag("Bike"))
            {
                BikeController bikeController = other.GetComponent<BikeController>();
                if (bikeController != null)
                {
                    bikeController.SetShieldBehaviour(shieldBehaviour);  
                    Destroy(gameObject);  
                }
            }
        }
    }
}

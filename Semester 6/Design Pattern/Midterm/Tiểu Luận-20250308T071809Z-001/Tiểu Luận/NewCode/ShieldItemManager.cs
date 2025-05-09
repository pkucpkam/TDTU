public class ShieldItemManager : MonoBehaviour
    {
        public GameObject shieldItemPrefab; 
        public float spawnInterval = 5f;  
        public float spawnRange = 10f;  

        void Start()
        {
            InvokeRepeating("SpawnShieldItem", 0f, spawnInterval);  
        }

        void SpawnShieldItem()
        {
            float spawnX = Random.Range(-spawnRange, spawnRange);  
            Vector3 spawnPosition = new Vector3(spawnX, 1f, 50f);  

            Instantiate(shieldItemPrefab, spawnPosition, Quaternion.identity);  
        }
    }

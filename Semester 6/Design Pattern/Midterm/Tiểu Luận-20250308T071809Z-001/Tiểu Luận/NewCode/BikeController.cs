﻿using UnityEngine;

namespace Chapter.Observer
{
    public class BikeController : Subject
    {
        public bool IsTurboOn
        {
            get; private set;
        }

        public float CurrentHealth
        {
            get { return health; } 
            
        }
        
        private bool _isEngineOn;
        private HUDController _hudController;
        private CameraController _cameraController;
        [SerializeField] private float health = 100.0f;
        private ISheildBehavior _shieldBehavior;
        
        void Awake()
        {
            _hudController = 
                gameObject.AddComponent<HUDController>();
            
            _cameraController = 
                (CameraController) 
                FindObjectOfType(typeof(CameraController));
        }

        private void Start()
        {
            StartEngine();
        }

        void OnEnable()
        {
            if (_hudController) 
                Attach(_hudController);
            
            if (_cameraController) 
                Attach(_cameraController);
        }
        
        void OnDisable()
        {
            if (_hudController) 
                Detach(_hudController);
            
            if (_cameraController) 
                Detach(_cameraController);
        }

        private void StartEngine()
        {
            _isEngineOn = true;
            
            NotifyObservers();
        }
        
        public void ToggleTurbo()
        {
            if (_isEngineOn) 
                IsTurboOn = !IsTurboOn;
            
            NotifyObservers();
        }
        
        public void TakeDamage(float amount)
        {

            if (_shieldBehavior != null)
            {
                amount = _shieldBehavior.AbsorbDamage(amount);
            }

            health -= amount;
            IsTurboOn = false;
            
            NotifyObservers();
            
            if (health < 0)
                Destroy(gameObject);
        }

        public void Heal(float amount)
        {
            health += amount;
            
            if (health > 100)
                health = 100;
            
            NotifyObservers();
        }
        
        public void SetShieldBehavior(ISheildBehavior shieldBehavior)
        {
            _shieldBehavior = shieldBehavior;
        }
    }
}
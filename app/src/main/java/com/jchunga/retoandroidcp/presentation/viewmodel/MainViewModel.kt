package com.jchunga.retoandroidcp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import com.jchunga.retoandroidcp.core.Tab
import com.jchunga.retoandroidcp.domain.model.AppState
import com.jchunga.retoandroidcp.domain.model.AuthState
import com.jchunga.retoandroidcp.domain.model.Candy
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import com.jchunga.retoandroidcp.domain.model.CreditCardInfo
import com.jchunga.retoandroidcp.domain.model.User
import com.jchunga.retoandroidcp.domain.usecase.GetCandyListCaseUse
import com.jchunga.retoandroidcp.domain.usecase.GetPremierListCaseUse
import com.jchunga.retoandroidcp.domain.usecase.PayingCaseUse
import com.jchunga.retoandroidcp.domain.usecase.SignInWithGoogleUseCase
//import com.jchunga.retoandroidcp.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPremierListCaseUse: GetPremierListCaseUse,
    private val getCandyListCaseUse: GetCandyListCaseUse,
    private val payingCaseUse: PayingCaseUse,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient


) : ViewModel(){

    private val _selectedTab = MutableLiveData<Tab>(Tab.Premier)
    val selectedTab: LiveData<Tab> = _selectedTab

    private val _state = MutableLiveData<AppState>(AppState())
    val state: LiveData<AppState> = _state

    //Formulario del card
    var _creditCardInfo  = MutableLiveData(CreditCardInfo())
    val creditCardInfo: LiveData<CreditCardInfo> = _creditCardInfo

    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    val authState: LiveData<AuthState> = _authState

    private val _cardNumberError = MutableLiveData<String?>()
    val cardNumberError: LiveData<String?> = _cardNumberError

    private val _cvvError = MutableLiveData<String?>()
    val cvvError: LiveData<String?> = _cvvError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> = _nameError

    private val _docError = MutableLiveData<String?>()
    val docError: LiveData<String?> = _docError

    private val _dateError = MutableLiveData<String?>()
    val dateError: LiveData<String?> = _dateError

    init {
        viewModelScope.launch {
            val response = getPremierListCaseUse()
            val resp = getCandyListCaseUse()
            _state.value = _state.value?.copy(
                premieres = response
            )
            _state.value = _state.value?.copy(
                candies = resp
            )
        }
    }

    fun signInWithGoogle(idToken: String, home:() ->Unit) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = signInWithGoogleUseCase(idToken)
            if (user != null) {
                _authState.value = AuthState.Success(user)
                val updateCard = user.email?.let { user.displayName?.let { it1 -> _creditCardInfo.value?.copy( email = it, name = it1) } }
                _creditCardInfo.value = updateCard
                home()
            } else {
                _authState.value = AuthState.Error("Authentication failed")
            }
        }
    }


    fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.currentUser
        return if (firebaseUser != null) {
            return User(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                displayName = firebaseUser.displayName,
                photoUrl = firebaseUser.photoUrl.toString()
            )
        } else {
            null
        }
    }

    suspend fun signOut() {
        firebaseAuth.signOut()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
            val signInMethods = firebaseAuth.fetchSignInMethodsForEmail(currentUser.email!!).await()
            if (signInMethods.signInMethods?.contains(GoogleAuthProvider.GOOGLE_SIGN_IN_METHOD) == true) {
                googleSignInClient.signOut().addOnCompleteListener {
                    // Acción adicional después de cerrar la sesión de Google, si es necesario
                }.await()
            }
        }

    }

    fun selectTab(tab: Tab) {
        _selectedTab.value = tab
    }


    fun incrementQuantity(index: Int) {
        _state.value = _state.value?.let { appState ->
            val updatedCandies = appState.candies.items.toMutableList().apply {
                val candy = this[index]
                this[index] = candy.copy(quantity = (if(this[index].quantity == null) 0 else this[index].quantity!!)  + 1)
            }
            val updatedTotalPrice = calculateTotalPrice(updatedCandies)

            appState.copy(candies = appState.candies.copy(items = updatedCandies), totalPrice = updatedTotalPrice )
        }
    }

    fun decrementQuantity(index: Int) {
        _state.value = _state.value?.let { appState ->
            val updatedCandies = appState.candies.items.toMutableList().apply {
                val candy = this[index]
                if ((if(this[index].quantity == null) 0 else this[index].quantity!!) > 0) {
                    this[index] = candy.copy(quantity = (if(this[index].quantity == null) 0 else this[index].quantity!!)  - 1)
                }
            }
            val updatedTotalPrice = calculateTotalPrice(updatedCandies)

            appState.copy(candies = appState.candies.copy(items = updatedCandies), totalPrice = updatedTotalPrice )
        }
    }

    private fun calculateTotalPrice(candies: List<Candy>) : Double{
        return candies.sumOf {
            it.price.toDouble() * (if(it.quantity == null) 0 else it.quantity!!)
        }
    }


    fun onCardNumberChange(cardNumber: String) {

        if(cardNumber.length < 17){
            val updateCard = _creditCardInfo.value?.copy( cardNumber = cardNumber )
            _creditCardInfo.value = updateCard
        }

        if(cardNumber.length >= 16 && cardNumber.all { it.isDigit() } ){
            _cardNumberError.value = null
        } else {
            _cardNumberError.value = "El número de tarjeta debe tener 16 dígitos numéricos"
        }
    }

    fun onExpirationDateChange(expirationDate: String) {

        val updateCard = _creditCardInfo.value?.copy( expirationDate = expirationDate )
        _creditCardInfo.value = updateCard
        val formattedDate = when (expirationDate.length) {
            1, 2 -> expirationDate
            3 -> if (expirationDate[2] == '/') expirationDate else "${expirationDate.substring(0, 2)}/${expirationDate[2]}"
            4, 5 -> if (expirationDate[2] == '/') expirationDate else "${expirationDate.substring(0, 2)}/${expirationDate.substring(2)}"
            else -> expirationDate
        }

    }

    fun onCvvChange(cvv: String) {
        if(cvv.length < 4){
            val updateCard = _creditCardInfo.value?.copy( cvv = cvv )
            _creditCardInfo.value = updateCard
        }
        if(cvv.length >= 2 && cvv.all { it.isDigit() } ){
            _cvvError.value = null
        } else {
            _cvvError.value = "Ingrese un CVV Valido"
        }
    }

    fun onEmailChange(email: String) {
        val updateCard = _creditCardInfo.value?.copy( email = email )
        _creditCardInfo.value = updateCard

        if(email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailError.value = null
        } else {
            _emailError.value = "Ingrese un correo valido"
        }

    }

    fun onNameChange(name: String) {
        val updateCard = _creditCardInfo.value?.copy( name = name )
        _creditCardInfo.value = updateCard
        if(name.isNotEmpty()){
            _nameError.value = null
        } else {
            _nameError.value = "Ingrese un nombre valido"
        }
    }

    fun onDocumentNumberChange(documentNumber: String) {
        val updateCard = _creditCardInfo.value?.copy( documentNumber = documentNumber )
        _creditCardInfo.value = updateCard

        if(documentNumber.length >= 8 && documentNumber.all { it.isDigit() } ){
            _docError.value = null
        } else {
            _docError.value = "Ingrese un número de documento valido"
        }
    }

    fun pay(cardInfo: CardInfoRequest): CardInfoResponse {
        var updateCard = _creditCardInfo.value?.copy( isLoading = true )
        _creditCardInfo.value = updateCard

        var resp = CardInfoResponse()
        viewModelScope.launch {
            resp = payingCaseUse(cardInfo)
        }
        updateCard = _creditCardInfo.value?.copy( isLoading = false )
        _creditCardInfo.value = updateCard
        return resp
    }


}


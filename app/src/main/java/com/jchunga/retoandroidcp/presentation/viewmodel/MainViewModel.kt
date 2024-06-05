package com.jchunga.retoandroidcp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPremierListCaseUse: GetPremierListCaseUse,
    private val getCandyListCaseUse: GetCandyListCaseUse,
    private val payingCaseUse: PayingCaseUse,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val firebaseAuth: FirebaseAuth
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
//
//
//    fun signOut() {
//        firebaseAuth.signOut()
//    }

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
        val updateCard = _creditCardInfo.value?.copy( cardNumber = cardNumber )
        _creditCardInfo.value = updateCard
    }

    fun onExpirationDateChange(expirationDate: String) {
        val updateCard = _creditCardInfo.value?.copy( expirationDate = expirationDate )
        _creditCardInfo.value = updateCard
    }

    fun onCvvChange(cvv: String) {
        val updateCard = _creditCardInfo.value?.copy( cvv = cvv )
        _creditCardInfo.value = updateCard
    }

    fun onEmailChange(email: String) {
        val updateCard = _creditCardInfo.value?.copy( email = email )
        _creditCardInfo.value = updateCard
    }

    fun onNameChange(name: String) {
        val updateCard = _creditCardInfo.value?.copy( name = name )
        _creditCardInfo.value = updateCard
    }

    fun onDocumentTypeChange(documentType: String) {
        val updateCard = _creditCardInfo.value?.copy( documentType = documentType )
        _creditCardInfo.value = updateCard
    }

    fun onDocumentNumberChange(documentNumber: String) {
        val updateCard = _creditCardInfo.value?.copy( documentNumber = documentNumber )
        _creditCardInfo.value = updateCard
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


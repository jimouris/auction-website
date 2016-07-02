package javauction.model;

/**
  * status of the operation that is being executed
  */
public enum OpStatus
{
    Success,
    Error,
    UsernameExist,
    DiffPass,
    IsAdmin,
    WrongCredentials,
    EmptySet
}
